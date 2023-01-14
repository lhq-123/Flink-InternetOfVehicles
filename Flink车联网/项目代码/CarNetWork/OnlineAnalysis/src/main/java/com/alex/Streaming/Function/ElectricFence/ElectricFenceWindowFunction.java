package com.alex.Streaming.Function.ElectricFence;

import com.google.common.collect.Lists;
import com.alex.Streaming.Bean.ElectricFence.ElectricFenceModel;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 20:46
 * @Description 自定义窗口函数，实现对90秒翻滚窗口中的每一个窗口进行计算，计算出电子围栏模型数据结果
 *          1.对单个窗口中的车辆数据根据terminalTime进行排序
 *          2.从redis中获取车辆vin对应的flag标记(车辆在围栏中或不在)
 *          3.遍历DStream<ElectricFenceModel>的元素
 *          4.对当前一个翻滚窗口中vin有多条数据，计算车辆在围栏中和围栏外的次数
 *             4.1 单个车辆出现在围栏中的次数和单个车辆出现在围栏外的次数
 *             4.2 根据每个vin和窗口间隔时间划分的单个窗口，相邻两条数据当前电子围栏状态，设置电子围栏模型对象
 *          5.写入车辆是否在电子围栏标记到state中作为缓存，flag：0代表在围栏中；flag：1，代表不在围栏中
 *          6.判断同一车辆在电子围栏中的flag之前是否存在与state中 且 判断flag是否成功写入state中
 *          7.如果经纬度、gpsTime都不为空，继续设置电子模型对象
 *          8.根据车辆最后一次的围栏状态和本次车辆的flag，设置电子围栏状态警告、进出围栏时间
 *             8.1 如果redis缓存中lastStatusRValue(flag)为1，现在flag为0，即是进围栏，则设置电子围栏状态警告为进围栏(1),
 *                 设置终端时间为进围栏时间，反之则为出围栏
 *   在通过实时分析车辆数据是否在电子围栏中，并得到分析后返回的DStream数据流：DataStream<ElectricFenceModel>时；
 *   在电子围栏分析结果数据落地之前，要考虑到除第一个窗口之外的实时数据进来，需去查询结果表数据，合并数据之后再入库，
 *   如车辆已存在结果表中，需更新，不存在则插入
 */
public class ElectricFenceWindowFunction extends RichWindowFunction<ElectricFenceModel,ElectricFenceModel,String, TimeWindow> {

    private static Logger logger = LoggerFactory.getLogger(ElectricFenceWindowFunction.class);
    String stateStartWith = "electricFence_";
    private MapState<String, Integer> state = null;

    /**
     * 初始化资源，只被执行一次
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //定义mapState的描述器
        MapStateDescriptor mapStateDescriptor = new MapStateDescriptor<String, Integer>(
                "valueState",
                TypeInformation.of(new TypeHint<String>() {
                }),
                TypeInformation.of(new TypeHint<Integer>() {
                }));

        //获取全局的参数配置
        ParameterTool parameterTool = (ParameterTool) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();

        // 默认存储900秒的历史围栏状态数据
        Integer timeOut = Integer.valueOf(parameterTool.getRequired("state.key.timeout"));

        //创建状态过期的配置对象
        StateTtlConfig ttlConfig = StateTtlConfig
                //存活时间
                .newBuilder(Time.seconds(timeOut))
                //配置UpdateType为OnCreateAndWrite，则每次更新 MapState 时都会更新 TTL Time；
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                //NeverReturnExpired: 永远不返回过期的用户数据
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();

        //设置state数据的过期策略
        mapStateDescriptor.enableTimeToLive(ttlConfig);

        //初始化MapState对象
        state = getRuntimeContext().getMapState(mapStateDescriptor);
    }

    /**
     *  自定义实现电子围栏的处理逻辑
     * @param key
     * @param timeWindow
     * @param iterable
     * @param collector
     * @throws Exception
     */
    @Override
    public void apply(String key, TimeWindow timeWindow, Iterable<ElectricFenceModel> iterable, Collector<ElectricFenceModel> collector) throws Exception {
        //定义需要返回的电子围栏模型对象实体类
        ElectricFenceModel electricFenceModel = new ElectricFenceModel();

        //使用google的工具类将迭代器转换成集合对象
        List<ElectricFenceModel> efModelList = Lists.newArrayList(iterable);
        //对单个窗口中的车辆数据进行根据terminalTime进行排序
        Collections.sort(efModelList);
        logger.info("进入电子围栏滚动窗口的windowFunction，输入数据数组长度为：" + efModelList.size());

        //从state中获取车辆vin对应的flag标记(车辆在围栏中或不在) 0：电子围栏里面 1：电子围栏外面
        //需要将当前的行驶的位置（围栏内还是围栏外）作为下一条数据的一个属性，所以将当前的状态保存到state中，
        // 可以利用flink——ValueState将上一次的历史状态存储到ValueState中
        Integer lastStatusValue = state.get(stateStartWith + key);
        logger.info("state中获得的state的value为" + lastStatusValue);
        if (lastStatusValue == null) {
            lastStatusValue = -999999;
        }

        //定义车辆在电子围栏内出现的次数
        long electricFenceInCount = efModelList.stream().filter(efModel -> (efModel.getNowStatus() == 0)).count();
        //定义车辆在电子围栏外出现的次数
        long electricFenceOutCount = efModelList.stream().filter(efModel -> (efModel.getNowStatus() == 1)).count();

        //定义当前电子围栏状态
        int currentStateValue = 1;
        // 90秒内车辆出现在围栏内的次数大于出现在围栏外的数据（以此判断车联在电子围栏内），则标记为0
        if (electricFenceInCount >= electricFenceOutCount) currentStateValue = 0;
        //将当前窗口的电子围栏状态记录下来
        state.put(stateStartWith + key, currentStateValue);
        //如果当前电子围栏状态与历史电子围栏状态不同
        if (lastStatusValue != currentStateValue) {
            //如果前后相邻的两个窗口的电子围栏状态不同，则需要处理数据
            //如果上一个窗口是电子围栏外，当前窗口是电子围栏内，则说明进入了电子围栏
            //如果上一个窗口没有记录电子围栏状态，当前窗口在电子围栏内，则说明进入电子围栏
            if ((lastStatusValue == 1 || lastStatusValue == -999999) && currentStateValue == 0) {
                ElectricFenceModel fenceModel = efModelList.stream().filter(efMode -> efMode.getNowStatus() == 0).findFirst().get();
                BeanUtils.copyProperties(electricFenceModel, fenceModel);
                //状态报警 0：出围栏 1：进围栏
                electricFenceModel.setStatusAlarm(1);
                //终端时间即进入电子围栏时间
                electricFenceModel.setInEleTime(fenceModel.getTerminalTime());
                collector.collect(electricFenceModel);
            }
            //如果上一个窗口是电子围栏内，当前窗口是电子围栏外，则说明退出了电子围栏
            //如果上一个窗口没有记录电子围栏状态，当前窗口在电子围栏外，则说明退出了电子围栏
            else if ((lastStatusValue == 0 || lastStatusValue == -999999) && currentStateValue == 1) {
                //获取当前窗口最后一条退出电子围栏的数据
                ElectricFenceModel fenceModel = efModelList.stream().filter(efMode -> efMode.getNowStatus() == 1).sorted(Comparator.reverseOrder()).findFirst().get();
                BeanUtils.copyProperties(electricFenceModel, fenceModel);
                //状态报警 0：出围栏 1：进围栏
                electricFenceModel.setStatusAlarm(0);
                //终端时间即进入电子围栏时间
                electricFenceModel.setOutEleTime(fenceModel.getTerminalTime());
                collector.collect(electricFenceModel);
            }
        }
    }
}
