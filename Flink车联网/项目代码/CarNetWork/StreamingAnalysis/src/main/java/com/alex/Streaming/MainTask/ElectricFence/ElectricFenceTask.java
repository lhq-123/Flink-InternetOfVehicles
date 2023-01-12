package com.alex.Streaming.MainTask.ElectricFence;

import com.alex.Streaming.Bean.ElectricFenceModel;
import com.alex.Streaming.Bean.ElectricFenceResultTmp;
import com.alex.Streaming.Function.ElectricFenceModelFunction;
import com.alex.Streaming.Function.ElectricFenceRulesFunction;
import com.alex.Streaming.Function.ElectricFenceWindowFunction;
import com.alex.Streaming.MainTask.BaseTask.BaseTask;
import com.alex.Streaming.Sink.ElectricFenceSink.ElectricFenceToMysqlSink;
import com.alex.Streaming.Source.MysqlElectricFenceSource;
import com.alex.Streaming.Source.MysqlVehicleInfoSource;
import com.alex.Streaming.Utils.JsonParsePartUtil;
import com.alex.Streaming.Utils.VehicleDataPartObj;
import com.alex.Streaming.Watermark.ElectricFenceWatermark;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.HashMap;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 11:49
 * @Description   电子围栏分析
 *          1.电子围栏分析任务设置、原始数据json解析、过滤异常数据
 *          2.读取已存在电子围栏中的车辆与电子围栏信息(广播流临时结果数据)
 *              2.1 添加Mysql电子围栏数据源
 *              2.2 定义source，实现加载电子围栏与电子围栏车辆数据
 *              2.3 写run方法，实现数据查询逻辑，并设置collect输出内容
 *              2.4 写cancel方法，实现资源释放，关闭连接
 *              2.5 置返回结果电子围栏与电子围栏车辆信息数据为广播流
 *          3.原始车辆数据与电子围栏广播流进行合并，生成电子围栏规则模型流数据（DStream<ElectricFenceModel>）
 *          4.创建90秒翻滚窗口，计算电子围栏信息(ElectricFenceModel中的值根据车辆是否在围栏内进行设置)
 *              4.1 自定义水印：ElectricFenceWatermark
 *              4.2 根据vin进行分组：electricFenceModel.getVin
 *              4.3 设置窗口为90间隔的翻滚窗口
 *              4.4 自定义窗口函数：ElectricFenceWindowFunction
 *          5.读取电子围栏分析结果表数据并广播
 *              5.1 读取电子围栏分析结果表：electric_fence，并广播传递给下一个任务节点
 *              5.2 自定义source，继承RichSourceFunction对象，重写run、cancel方法
 *              5.3 获得已存在结果表中的车辆和最早出现在结果表中的id，并广播结果流
 *          6.90秒翻滚窗口电子围栏对象模型流数据与电子围栏分析结果数据广播流进行connect
 *          7.对电子围栏对象模型，添加uuid和inMysql(车辆是否已存在mysql表中)
 *              7.1 车辆是否已存在mysql表中,已存在则设置电子围栏id为uuid，设置当前车辆是否在mysql中为true
 *              7.2 不存在mysql中，则设置uuid为Long的最大值减去当前时间(不重复的随机值）
 *          8.电子围栏分析结果数据落地mysql，与第五步读取mysql中电子围栏分析结果的表是同一张表:electric_fence
 *              8.1 输出流设置sink
 *              8.2 自定义mysql电子围栏分析结果表sink
 *              8.3 重写open方法，实现加载mysql驱动，获得连接
 *              8.4 重写invoke方法，实现出围栏对原车辆存储数据进行更新(未更新之前的状态为在围栏中)，另实现进围栏对新增车辆数据进行插入到数据库中
 *              8.5 写close方法，关闭mysql连接，释放资源
 */
public class ElectricFenceTask extends BaseTask {
    public static void main(String[] args) throws Exception{
        //TODO 1）电子围栏分析任务设置，原始数据JSON解析，过滤出异常输出
        StreamExecutionEnvironment env = getEnv(ElectricFenceTask.class.getSimpleName());
        //TODO 1.1）将kafka消费者实例添加到环境中
        DataStream<String> dataStreamSource = createKafkaStream(SimpleStringSchema.class);
        //TODO 1.2）将字符串转换成JSON对象，过滤出来正常的数据;过滤出来异常数据，查询到正常数据返回
        SingleOutputStreamOperator<VehicleDataPartObj> vehicleJsonDataStream =
                dataStreamSource.map((MapFunction<String, VehicleDataPartObj>) obj -> {
            JsonParsePartUtil jsonParsePartUtil = new JsonParsePartUtil();
            return jsonParsePartUtil.parseJsonToObject(obj);
        }).filter(vehicleDataPartObj -> StringUtils.isEmpty(vehicleDataPartObj.getErrorData()));
        vehicleJsonDataStream.print("原始数据>>>>");
        //TODO 2）读取已存在电子围栏中的车辆与电子围栏信息，广播临时数据
        DataStream<HashMap<String, ElectricFenceResultTmp>> electricFenceBroadcastStream = env.addSource(new MysqlElectricFenceSource()).broadcast();
        //TODO 3）原始车辆数据与电子围栏广播流进行合并，生成电子围栏规则模型流数据（DStream<ElectricFenceModel>）
        ConnectedStreams<VehicleDataPartObj, HashMap<String, ElectricFenceResultTmp>> electricFenceConnectStream = vehicleJsonDataStream.connect(electricFenceBroadcastStream);
        SingleOutputStreamOperator<ElectricFenceModel> electricFenceModelDataStream = electricFenceConnectStream.flatMap(new ElectricFenceRulesFunction());
        electricFenceModelDataStream.printToErr("电子围栏车辆规则与原始数据关联后的数据>>>>");
        //TODO 4）创建90秒翻滚窗口，计算电子围栏信息，电子围栏规则模型流生成水印、分组、创建翻滚窗口、创建窗口函数
        DataStream<ElectricFenceModel> electricFenceDataStream = electricFenceModelDataStream.assignTimestampsAndWatermarks(new ElectricFenceWatermark())
                .keyBy(ElectricFenceModel::getVin)
                .window(TumblingEventTimeWindows.of(Time.seconds(90)))
                .apply(new ElectricFenceWindowFunction());
        //TODO 5）读取电子围栏分析结果表数据并广播
        DataStream vehicleInfoStream = env.addSource(new MysqlVehicleInfoSource()).broadcast();
        //TODO 6）90秒翻滚窗口电子围栏对象模型流数据与电子围栏分析结果数据广播流进行connect
        ConnectedStreams connectedStreams = electricFenceDataStream.connect(vehicleInfoStream);
        //TODO 7）判断窗口中车辆是否已存在Mysql结果表中，对电子围栏对象模型，添加uuid和inMysql
        DataStream<ElectricFenceModel> mysqlStream  = connectedStreams.flatMap(new ElectricFenceModelFunction());
        //TODO 8）电子围栏分析结果数据落地mysql
        mysqlStream.addSink(new ElectricFenceToMysqlSink());
        env.execute();
    }
}

