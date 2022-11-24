package com.lhq.Streaming.Source;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * @author liu
 * @Create 2022-11-05
 * @Description 使用Flink将解析后的原始数据写入到Kafka分区中（从HDFS上读取数据文件将数据读取到kafka主题里）
 */
public class FlinkWriteToKafka {
    public static void main(String[] args) {
        //1 开启流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2 设置并行度，checkpoint，重启策略等参数
        env.setParallelism(1);
        //2.1设置应应用程序按照事件时间处理数据
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //3 读取数据
        //DataStreamSource<String> Source = env.readTextFile("C:\\Users\\admin\\Desktop\\Flink车联网\\原始数据\\sourcedata.txt");
        DataStreamSource<String> Source = env.readTextFile("hdfs://Flink01:8020/FlinkCarDataSource/sourcedata.txt");
        //4 创建FlinkKafkaProducer类
            //4.1配置属性
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"Flink01:9092,Flink02:9092,Flink03:9092");
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"5");
        props.setProperty(ProducerConfig.ACKS_CONFIG,"0");
            //4.2实例化FlinkKafkaProducer类
        FlinkKafkaProducer<String> producer = new FlinkKafkaProducer<>(
                "vehicledata",
                new KafkaSerializationSchema<String>() {
                    @Override
                    public ProducerRecord<byte[], byte[]> serialize(String element, @Nullable Long timestamp) {
                        return new ProducerRecord<>(
                                "vehicledata",
                                element.getBytes()
                        );
                    }
                },
                props,
                FlinkKafkaProducer.Semantic.NONE
        );
        //5 设置数据源
        Source.addSink(producer);
        //6 执行流处理环境
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
