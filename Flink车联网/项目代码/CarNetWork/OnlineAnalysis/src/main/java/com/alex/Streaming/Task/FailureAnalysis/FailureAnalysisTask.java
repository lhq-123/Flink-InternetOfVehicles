package com.alex.Streaming.Task.FailureAnalysis;

import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisDataObj;
import com.alex.Streaming.Bean.FailureAnalysis.FailureAnalysisModel;
import com.alex.Streaming.Function.FailureAnalysis.AsyncHttpQueryFunction;
import com.alex.Streaming.Function.FailureAnalysis.FailureAnalysisVehicleInfoRedisFunction;
import com.alex.Streaming.Function.FailureAnalysis.FailureAnalysisVehicleMapMysqlFunction;
import com.alex.Streaming.Function.FailureAnalysis.FailureAnalysisWindowFunction;
import com.alex.Streaming.Task.BaseTask.BaseTask;
import com.alex.Streaming.Source.MysqlFailureAnalysisVehicleInfoSource;
import com.alex.Streaming.Utils.JsonParsePartUtil;
import com.alex.Streaming.Bean.SourceData.VehicleDataPartObj;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Alex_liu
 * @create 2023-01-13 10:57
 * @Description  远程诊断实时故障分析，消费kafka数据进行实时故障分析，分析结果写入到mysql数据库中
 *               1.创建流式环境，设置流式环境相关参数
 *               2.加载kafka数据源，过滤掉异常数据，过滤出来正常数据
 *               3.创建原始数据的30s的滚动窗口，根据vin进行分组
 *               4.对原始数据的窗口流数据进行实时故障分析核心逻辑的实现（区分出来告警数据以及非告警数据）
 *               5.加载业务中间表（7张表）数据，合并为车型、车系、车辆销售信息的DataStream，然后广播
 *               6.将第四步产生的窗口流数据和第五步产生的广播流数据进行合并
 *               7.加载地理位置信息表数据，进行广播
 *               8.对第六步的窗口流数据和第七步产生的地理位置信息广播流数据进行合并
 *               9.最终第八步产生的结果数据落地到mysql表中
 *               10.执行任务，查看分析结果
 */
public class FailureAnalysisTask extends BaseTask {
    public static void main(String[] args) throws Exception {
        //TODO 1）创建流式环境，设置流式环境相关参数
        StreamExecutionEnvironment env = getEnv(FailureAnalysisTask.class.getSimpleName());
        //TODO 2）加载kafka数据源，过滤掉异常数据，过滤出来正常数据
        DataStream<String> kafkaStreamSource = createKafkaStream(SimpleStringSchema.class);
        kafkaStreamSource.print("原始数据>>>>>");
        //将字符串转换成javaBean
        SingleOutputStreamOperator<VehicleDataPartObj> vehicleDataPartDataStream = kafkaStreamSource.map((MapFunction<String, VehicleDataPartObj>) obj -> {
            JsonParsePartUtil jsonParsePartUtil = new JsonParsePartUtil();
            return jsonParsePartUtil.parseJsonToObject(obj);
            //过滤出异常数据，返回正常数据
        }).filter(vehicleDataPartObj -> vehicleDataPartObj.getErrorData().isEmpty());
        vehicleDataPartDataStream.print("原始数据过滤出来的正常数据>>>>>");
        //TODO 3）创建原始数据的30s的滚动窗口(本质上为翻滚窗口)，根据vin进行分组
        WindowedStream<VehicleDataPartObj, String, TimeWindow> vehicleDataPartObjWindowedStream = vehicleDataPartDataStream.keyBy(vehicleDataPartObj -> vehicleDataPartObj.getVin()).timeWindow(Time.seconds(30));
        //TODO 4）核心逻辑，对原始数据的窗口流数据进行实时故障分析核心逻辑的实现（区分出来告警数据以及非告警数据），自定义WindowFunction，解决窗口计算返回输出对象(20个属性)
        SingleOutputStreamOperator<FailureAnalysisDataObj> failureAnalysisObjDataStream = vehicleDataPartObjWindowedStream.apply(new FailureAnalysisWindowFunction());
        failureAnalysisObjDataStream.print("自定义窗口的数据>>>>>");
        //TODO 5）车型车系销售信息广播流，加载车型车系车辆销售信息数据源，加载业务中间表（7张表）数据，合并为车型、车系、车辆销售信息的DataStream，然后广播
        DataStream<HashMap<String, FailureAnalysisModel>> failureAnalysisVehicleInfoDataStream = env.addSource(new MysqlFailureAnalysisVehicleInfoSource()).broadcast();
        //TODO 6）窗口数据流与车型车系销售信息广播流进行连接，输出25个属性
        SingleOutputStreamOperator<FailureAnalysisDataObj> connectedFailureAnalysisVehicleDataStream = failureAnalysisObjDataStream.connect(failureAnalysisVehicleInfoDataStream).flatMap(new FailureAnalysisVehicleMapMysqlFunction());
        //TODO 7）查询redis中存在指定的地理位置信息，根据GeoHash值获取到地理位置信息
        SingleOutputStreamOperator<FailureAnalysisDataObj> failureAnalysisVehicleWithLocationInfoDataStream = connectedFailureAnalysisVehicleDataStream.map(new FailureAnalysisVehicleInfoRedisFunction());
        //查询Redis中不存在指定的地理位置信息，访问高德地图开放者api根据经纬度获取逆地理位置信息，获取到拉宽成功的地理位置信息数据
        SingleOutputStreamOperator<FailureAnalysisDataObj> failureAnalysisVehicleNoWithLocationInfoDataStream = failureAnalysisVehicleWithLocationInfoDataStream.filter(failureAnalysisDataObj -> failureAnalysisDataObj.getProvince() != null);
        //异步请求高德地图根据经纬度获取到地理位置信息
        SingleOutputStreamOperator<FailureAnalysisDataObj> asyncFailureAnalysisDataObjWithLocationDataStream = AsyncDataStream.unorderedWait(failureAnalysisVehicleNoWithLocationInfoDataStream, new AsyncHttpQueryFunction(), 2000, TimeUnit.MILLISECONDS, 10);
        //TODO 8）将第六步和第七步产生的地理位置信息广播流数据进行合并，将redis查询到的地理位置数据与请求高德地图api获取到的地理位置数据进行组合
        DataStream<FailureAnalysisDataObj> resultDataStream = failureAnalysisVehicleWithLocationInfoDataStream.union(asyncFailureAnalysisDataObjWithLocationDataStream);
        resultDataStream.print("带有地理位置信息的数据>>>>>");
        //TODO 9）实时故障分析结果落地到mysql中

        env.execute();

    }
}
