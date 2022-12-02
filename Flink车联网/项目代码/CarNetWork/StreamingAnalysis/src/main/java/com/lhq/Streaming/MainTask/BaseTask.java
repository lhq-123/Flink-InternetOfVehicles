package com.lhq.Streaming.MainTask;

import com.lhq.Streaming.MainTask.SourceDataProcessing.KafkaSourceDataTask;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *
 *  定义所有task作业的父类，在父类中实现公共的代码
 *  加载配置文件内容到ParameterTool对象中
 *  1）flink流处理环境的初始化
 *  2）flink接入kafka数据源消费数据
 */
public abstract class BaseTask {
    //定义parameterTool工具类
    public static ParameterTool parameterTool;
    public static String appName;

     //定义静态代码块，加载配置文件数据到ParameterTool对象中
    static {
        try {
            parameterTool = ParameterTool.fromPropertiesFile(BaseTask.class.getClassLoader().getResourceAsStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        //TODO 1）初始化flink流式处理的开发环境
    private static StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //TODO 2）flink任务的初始化方法
    public static StreamExecutionEnvironment getEnv(String className){
        System.setProperty("HADOOP_USER_NAME", "root");
        //设置全局的参数（使用的时候可以直接用法：getRuntimeContext()）
        env.getConfig().setGlobalJobParameters(parameterTool);
        //TODO 3）按照事件时间处理数据（terminalTimeStamp）进行窗口的划分和水印的添加
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //为了后续进行测试方便，将并行度设置为1，在生产环境一定不要设置代码级别的并行度，可以设置client级别的并行度
        env.setParallelism(1);
        //TODO 4）开启checkpoint
        //  TODO 4.1）设置每隔30s周期性开启checkpoint
        env.enableCheckpointing(30*1000);
        //  TODO 4.2）设置检查点的model、exactly-once、保证数据一次性语义
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        //  TODO 4.3）设置两次checkpoint的时间间隔，避免两次间隔太近导致频繁的checkpoint，而出现业务处理能力下降
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(20 * 1000);
        //  TODO 4.4）设置checkpoint的超时时间
        env.getCheckpointConfig().setCheckpointTimeout(20*1000);
        //  TODO 4.5）设置checkpoint的最大尝试次数，同一个时间有几个checkpoint在运行
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        //  TODO 4.6）设置checkpoint取消的时候，是否保留checkpoint，checkpoint默认会在job取消的时候删除checkpoint
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        //  TODO 4.7）设置执行job过程中，保存检查点错误时，job不失败
        env.getCheckpointConfig().setFailOnCheckpointingErrors(false);
        //  TODO 4.8）设置检查点的存储位置，使用rocketDBStateBackend，存储本地+hdfs分布式文件系统，可以进行增量检查点
        String hdfsUri = parameterTool.getRequired("hdfsUri");
        env.setStateBackend(new HashMapStateBackend());
        env.getCheckpointConfig().setCheckpointStorage(new FileSystemCheckpointStorage(hdfsUri+"/flink/checkpoint/"+ KafkaSourceDataTask.class.getSimpleName()));
        //TODO 5）设置任务的重启策略（固定延迟重启策略、失败率重启策略、无重启策略）
        //  TODO 5.1）如果开启了checkpoint，默认不停的重启，没有开启checkpoint，无重启策略
        env.setRestartStrategy(RestartStrategies.fallBackRestart());

        appName = className;
        //返回env对象
        return env;
    }

    /**
     *    flink接入kafka数据源消费数据
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> DataStream<T> createKafkaStream(Class<? extends DeserializationSchema> clazz) throws IllegalAccessException, InstantiationException {
        //TODO 6）创建flink消费kafka数据的对象，指定kafka的参数信息
        Properties props = new Properties();
        //  TODO 6.1）设置kafka的集群地址
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, parameterTool.getRequired("bootstrap.servers"));
        //  TODO 6.2）设置消费者组id
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, parameterTool.getRequired("kafka.group.id")+appName);
        //  TODO 6.3）设置kafka的分区感知（动态感知）
        props.setProperty("flink.partition-discovery.interval-millis", "30000");
        //  TODO 6.4）设置key和value的反序列化（可选）
        //  TODO 6.5）设置是否自动递交offset
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, parameterTool.get("enable.auto.reset", "earliest"));
        //  TODO 6.6）创建kafka的消费者实例
        FlinkKafkaConsumer<T> kafkaConsumer= new FlinkKafkaConsumer<T>(
                parameterTool.getRequired("kafka.topic"),
                clazz.newInstance(),
                props
        );
        //  TODO 6.7）设置自动递交offset保存到检查点
        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
        kafkaConsumer.setStartFromEarliest();
        //TODO 7）将kafka消费者对象添加到环境中
        DataStreamSource streamSource = env.addSource(kafkaConsumer);
        //返回消费到的数据
        return streamSource;
    }

    /**
     * 将数据流以文件形式写入HDFS
     * @param prefix
     * @param suffix
     * @param path
     * @param bucketAssignerFormat
     * @return
     */
    public static StreamingFileSink<String> createSink(
            String prefix,
            String suffix,
            String path,
            String bucketAssignerFormat
    ){
        OutputFileConfig config = OutputFileConfig.builder().withPartPrefix(prefix).withPartSuffix(suffix).build();
        StreamingFileSink DataSink = StreamingFileSink.forRowFormat(
                new Path(parameterTool.getRequired("hdfsUri") + "/user/hive/apps/warehouse/flinkcar/vehicle_ods/" + path),
                new SimpleStringEncoder<>("UTF-8"))
                .withBucketAssigner(new DateTimeBucketAssigner<>(bucketAssignerFormat))
                .withRollingPolicy(DefaultRollingPolicy.builder()
                        //设置滚动时间间隔，5秒钟产生一个文件
                        .withRolloverInterval(TimeUnit.SECONDS.toMillis(2))
                        //设置不活动的时间间隔，未写入数据处于不活动状态时滚动文件
                        .withInactivityInterval(TimeUnit.SECONDS.toMillis(2))
                        //文件大小，默认是128M滚动一次
                        .withMaxPartSize(128 * 1024 * 1024).build())
                .withOutputFileConfig(config).build();
        return DataSink;
    }
}
