package com.lhq.Streaming.MainTask.SourceDataProcessing;

import com.lhq.Streaming.MainTask.BaseTask;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import java.util.Properties;

/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *
 *  消费kafka主题的实时流作业
 */
public class KafkaSourceDataTask {
    public static void main(String[] args) throws Exception {
        //TODO 0）加载conf.properties配置文件，返回ParameterTool工具类对象
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(BaseTask.class.getClassLoader().getResourceAsStream("conf.properties"));
        //TODO 1）初始化flink流式处理的开发环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.getConfig().setGlobalJobParameters(parameterTool);
        //TODO 2）设置按照事件时间处理数据（划分窗口或者添加水印都需要事件时间）
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //TODO 3）开启checkpoint
            //TODO 3.1）设置每隔30秒钟开启checkpoint
            env.enableCheckpointing(30*1000L);
            //TODO 3.2）设置检查点的model，exactly-once，保证数据消费一次，数据不重复消费
            env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
            //TODO 3.3）设置两次checkpoint时间间隔，避免两次间隔太近导致频繁checkpoint而出现业务处理能力下降
            env.getCheckpointConfig().setMinPauseBetweenCheckpoints(20000);
            //TODO 3.4）设置checkpoint的超时时间
            env.getCheckpointConfig().setCheckpointTimeout(20000);
            //TODO 3.5）设置checkpoint最大的尝试次数，同一个时间有几个checkpoint并行执行
            env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
            //TODO 3.6）设置checkpoint取消的时候，是否保留checkpoint，checkpoint默认会在job取消的时候删除
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            //TODO 3.7）设置执行job过程中，保存检查点错误时，job不失败
            env.getCheckpointConfig().setFailOnCheckpointingErrors(false);
            //TODO 3.8）设置检查点存储的位置及状态后端，使用rocksDBStateBackend，存储到本地+hdfs分布式文件，增量检查点
            String hdfsUri = parameterTool.getRequired("hdfsUri");
            env.setStateBackend(new HashMapStateBackend());
            env.getCheckpointConfig().setCheckpointStorage(new FileSystemCheckpointStorage(hdfsUri+"/flink/checkpoint"+KafkaSourceDataTask.class.getSimpleName()));
            System.setProperty("HADOOP_USER_NAME","root");
            //TODO 3.9）设置任务的重启策略（固定延迟重启策略、失败率重启策略、无重启策略）
            env.setRestartStrategy(RestartStrategies.noRestart());
        //TODO 4）创建flink消费kafka数据的对象，指定kafka的参数信息
        Properties props = new Properties();
            //TODO 4.1）设置kafka集群地址
            props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,parameterTool.getRequired("bootstrap.servers"));
            //TODO 4.2）设置消费者组id
            props.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"vehicle_data_group");
            //TODO 4.3）设置kafka的分区感知（动态监测）
            props.setProperty("flink.partition-discovery.interval-millis", "30000");
            //TODO 4.5）设置自动递交offset位置策略
            props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
            props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
            //TODO 4.6）创建kafka的消费者实例
            FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
                parameterTool.getRequired("kafka.topic"),
                new SimpleStringSchema(),
                props
            );
            //TODO 4.7）设置自动递交offset保存到检查点
            kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
        //TODO 5）将kafka消费者对象添加到环境中
        DataStreamSource<String> dataStreamSource = env.addSource(kafkaConsumer);
        dataStreamSource.print();
        //TODO 6）启动作业，运行任务
        env.execute();
    }
}
