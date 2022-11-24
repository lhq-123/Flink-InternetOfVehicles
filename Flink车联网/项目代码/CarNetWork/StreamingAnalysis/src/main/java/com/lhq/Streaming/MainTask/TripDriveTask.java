package com.lhq.Streaming.MainTask;

import com.lhq.Streaming.Watermark.TripDriveWatermark;
import com.lhq.Streaming.Function.TripDriveSampleWindowFunction;
import com.lhq.Streaming.Sink.TripDriveSampleToHBaseSink;
import com.lhq.Streaming.Utils.JsonParseUtil;
import com.lhq.Streaming.Utils.VehicleDataObj;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * @author liu
 * @Create 2022-11-22
 * @Description
 *
 *  驾驶行程业务开发
 *  1）消费kafka数据过滤出来驾驶行程采样数据，实时的写入到hbase表中
 *  2）消费kafka数据过滤出来驾驶行程数据，实时的写入到hbase表中
 *
 */
public class TripDriveTask extends BaseTask{
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        //TODO 1）初始化流式环境
        StreamExecutionEnvironment env = getEnv(TripDriveTask.class.getSimpleName());
        //TODO 7）将kafka消费者对象添加到环境里
        DataStream<String> dataStreamSource = createKafkaStream(SimpleStringSchema.class);
        //TODO 8）将消费出来的JSON数据解析成javaBean对象
        SingleOutputStreamOperator<VehicleDataObj> vehicleJsonStream = dataStreamSource.map(JsonParseUtil::parseJsonToObject)
                //过滤出来驾驶行程数据
                //充电状态为行车充电(chargeStatus=2)、未充电(=3)的数据为驾驶行程数据
                //否则充电状态为停车充电(=1)、充电完成(=4)则为驾驶充电行程数据
                .filter(vehicleDataObj -> 2 == vehicleDataObj.getChargeStatus() || 3 == vehicleDataObj.getChargeStatus());
        //TODO 9）根据驾驶行程添加水位线
        //通过事件戳定期分配时间生成水位线(AssignerWithPeriodicWatermarks),需定义要发送的水位线
        //WindowedStream<VehicleDataObj, String, TimeWindow> tripDriveDataStream = vehicleJsonStream.assignTimestampsAndWatermarks(new TripDriveWatermark()).keyBy(VehicleDataObj::getVin).window(EventTimeSessionWindows.withGap(Time.minutes(15)));
            SingleOutputStreamOperator<VehicleDataObj> tripDriveWatermark = vehicleJsonStream.assignTimestampsAndWatermarks(new TripDriveWatermark());
            //TODO 9.1）根据vin进行分组
            KeyedStream<VehicleDataObj, String> keyedStream = tripDriveWatermark.keyBy(VehicleDataObj::getVin);
            //TODO 9.2）根据行程分析创建会话窗口(15分钟划分一个行程)
            WindowedStream<VehicleDataObj, String, TimeWindow> tripDriveDataStreamWindow = keyedStream.window(EventTimeSessionWindows.withGap(Time.minutes(15)));
        //TODO 10）驾驶行程采样分析与入库
        //SingleOutputStreamOperator<String[]> tripDataStream = tripDriveDataStreamWindow.apply(new TripDriveWindowFunction());
        tripDriveDataStreamWindow.apply(new TripDriveSampleWindowFunction()).addSink(new TripDriveSampleToHBaseSink("TRIPDB:trip_sample"));
        //TODO 11）驾驶行程划分与入库

        //TODO 12）启动流式任务
        try {
            env.execute("TripDriveTask");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
