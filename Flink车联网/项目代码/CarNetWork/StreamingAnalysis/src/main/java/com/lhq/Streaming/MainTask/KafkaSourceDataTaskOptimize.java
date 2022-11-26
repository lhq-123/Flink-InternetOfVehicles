package com.lhq.Streaming.MainTask;

import com.lhq.Streaming.Sink.ToHBaseDetailSinkOptimize;
import com.lhq.Streaming.Sink.ToHBaseSinkOptimize;
import com.lhq.Streaming.Utils.JsonParseUtil;
import com.lhq.Streaming.Utils.VehicleDataObj;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;


/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *    调用flink工具类初始化task作业
 */
public class KafkaSourceDataTaskOptimize extends BaseTask{
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = getEnv(KafkaSourceDataTask.class.getSimpleName());
        DataStream<String> kafkaDataStreamSource = createKafkaStream(SimpleStringSchema.class);
        //TODO 7）将json字符串解析成对象
        SingleOutputStreamOperator<VehicleDataObj> vehicleDataStream= kafkaDataStreamSource.map(JsonParseUtil::parseJsonToObject);
        //TODO 8）获取到正常的数据
        SingleOutputStreamOperator<VehicleDataObj> rightDataStream = vehicleDataStream.filter(vehicleDataObj -> StringUtils.isEmpty(vehicleDataObj.getErrorData()));
        rightDataStream.print("正常数据>>>");
        //TODO 8）获取到异常的数据
        SingleOutputStreamOperator<VehicleDataObj> errorDataStream = vehicleDataStream.filter(vehicleDataObj -> !StringUtils.isEmpty(vehicleDataObj.getErrorData()));
        errorDataStream.printToErr("异常数据>>>");
        //TODO 9）将异常的数据写入到hdfs中
        StreamingFileSink<String> errorDataSink = createSink(
                "vehicle",
                ".txt",
                "vehicle_errordata",
                "yyyyMMdd"
        );
        errorDataStream.map(VehicleDataObj::toHiveString).addSink(errorDataSink);
        //TODO 10）将正常的数据写入hdfs(离线分析)
        StreamingFileSink<String> rightDataSink = createSink(
                "vehicle",
                ".txt",
                "vehicle_rightdata",
                "yyyyMMdd"
        );
        rightDataStream.map(VehicleDataObj::toHiveString).addSink(rightDataSink);
        //TODO 11）将正确的数据写入到HBase(实时分析)
        ToHBaseSinkOptimize vehicle_rightdataSink = new ToHBaseSinkOptimize("vehicle_rightdata");
        rightDataStream.addSink(vehicle_rightdataSink);

        //TODO 12）将正确数据中经常用于分析的字段提取出来保存到一个hbase独立的表中，这个表的字段要远远小于正常数据宽表的字段数量
        //将来与Phoenix整合以后，在可视化页面工具中分析查询hbase表数据
        ToHBaseDetailSinkOptimize vehicle_rightdata_detailSink = new ToHBaseDetailSinkOptimize("vehicle_rightdata_detail");
        rightDataStream.addSink(vehicle_rightdata_detailSink);
        //TODO ）启动实时任务

        env.execute();
    }
}
