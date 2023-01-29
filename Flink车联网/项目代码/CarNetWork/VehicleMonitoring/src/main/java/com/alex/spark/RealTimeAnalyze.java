package com.alex.spark;

import com.alex.constants.Constants;
import com.alex.utils.ConfigUtil;
import org.apache.commons.codec.StringDecoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Alex_liu
 * @create 2023-01-29 10:22
 * @Description 道路车流量实时监控
 */
public class RealTimeAnalyze {
    private final static Logger logger = LoggerFactory.getLogger(RealTimeAnalyze.class);
    public static void main(String[] args) throws InterruptedException {
        //TODO 1）构建SparkStreaming上下文
        /*
        *  第一步:配置SparkConf
        *  1.至少两条线程因为Spark Streaming应用程序在运行的时候至少有一条线程用于不断地循环接受程序，并且至少有一
        *    条线程用于处理接受的数据（否则的话有线程用于处理数据，随着时间的推移内存和磁盘都会不堪重负）
        *  2.对于集群而言，每个Executor一般肯定不止一个线程，那对于处理SparkStreaming应用程序而言，每个Executor一般
        *    分配多少Core比较合适？根据我们过去的经验，5个左右的Core是最佳的（一个段子分配为奇数个Core表现最佳，例如3个，5个，7个Core等）
        */
        SparkConf sc = new SparkConf()
                .setMaster("local")
                .setAppName("RealTimeVehicleMonitoring")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .set("spark.default.parallelism", "1000")
                .set("spark.streaming.blockInterval", "50")
                .set("spark.streaming.receiver.writeAheadLog.enable", "true");
        /**
         *  第二步:创建SparkStreamingContext
         *  1.这个是SparkStreaming应用春香所有功能的起始点和程序调度的核心SparkStreamingContext的构建可以基于
         *    SparkConf参数也可以基于持久化的SparkStreamingContext的内容来恢复过来（典型的场景是Driver崩溃后重新
         *    启动，由于SparkStreaming具有连续7*24小时不间断运行的特征，所以需要Driver重新启动后继续上一次的状态，
         *    此时的状态恢复需要基于曾经的Checkpoint）
         *  2.在一个SparkStreaming 应用程序中可以创建若干个SparkStreaming对象，使用下一个SparkStreaming之前需要把
         *    前面正在运行的SparkStreamingContext对象关闭掉，由此，我们获取一个重大的启发我们获得一个重大的启发
         *    SparkStreaming也只是SparkCore上的一个应用程序而已，只不过SparkStreaming框架想运行的话需要spark工程师写业务逻辑
         */
        @SuppressWarnings("resource")
        JavaStreamingContext jsc = new JavaStreamingContext(sc, Durations.seconds(5));
        jsc.sparkContext().setLogLevel("WARN");
        //jsc.checkpoint("hdfs://Flink01:8020/SparkCheckpoint");
        Map<String, Object> kafkaParams = new HashMap<>();
        String brokers = ConfigUtil.getProperty(Constants.KAFKA_METADATA_BROKER_LIST);
        kafkaParams.put("bootstrap.servers",brokers);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);

        //TODO 2）构建topic set
        String kafkaTopics = ConfigUtil.getProperty(Constants.KAFKA_TOPICS);
        String[] kafkaTopic = kafkaTopics.split(",");
        HashSet<String> topics = new HashSet<>();
        for (String topic:kafkaTopic) {
            topics.add(topic);
        }

        //TODO 3）使用Kafka参数创建kafka消费者数据流
        JavaInputDStream<ConsumerRecord<String, String>> vehicleRealTimeDStream = KafkaUtils.createDirectStream(
                jsc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams));

//        JavaPairInputDStream<String,String> lines = KafkaUtils.createDirectStream(jsc,
//                String.class, String.class,
//                StringDecoder.class, StringDecoder.class,
//                kafkaParams,
//                topics);
        //TODO 4）实时计算道路的拥堵情况
        realTimeRoadState(vehicleRealTimeDStream);

        //TODO 5）动态改变广播变量
//        String path = "hdfs://Flink01:8020/Spark/ControlVehicle.txt";
//        controlVehicle(path,vehicleRealTimeDStream);

        //TODO 6）开启/停止任务
        jsc.start();
        jsc.awaitTermination();
        jsc.stop();
    }
    private static void controlVehicle(final String path, JavaPairInputDStream<String, String> vehicleRealTimeDStream) {
        vehicleRealTimeDStream.transform(new Function<JavaPairRDD<String, String>, JavaRDD<String>>() {
            private static final long serialVersionUID = 1L;
            @SuppressWarnings("resource")
            @Override
            public JavaRDD<String> call(JavaPairRDD<String, String> rdd) throws Exception {
                SparkContext context = rdd.context();
                JavaSparkContext jsc = new JavaSparkContext(context);
                List<String> blackVehicle = readFile(path);
                final Broadcast<List<String>> broadcast = jsc.broadcast(blackVehicle);
                List<String> value = broadcast.value();
                for (String s:value) {
                    logger.info("广播变量的值:"+s);
                }
                JavaRDD<String> map = rdd.filter(new Function<Tuple2<String, String>, Boolean>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Boolean call(Tuple2<String, String> tuple) throws Exception {
                        List<String> list = broadcast.value();
                        return list.contains(tuple._2.split("\t")[3]);
                    }
                }).map(new Function<Tuple2<String, String>, String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String call(Tuple2<String, String> tuple) throws Exception {
                        //卡扣号，以及action_time   直接写入到数据库中
                        //每一个log 是要缉查布控的车辆的详细信息
                        String log = tuple._2;
                        //通过log这一条数据，能够截取到monitorId，action_time
                        log.split("\t");
                        return log;
                    }
                });
                return map;
            }
        });
    }
    public static List<String> readFile(String path){
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = br.readLine();
            while (line != null){
                list.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    private static void realTimeRoadState(JavaInputDStream<ConsumerRecord<String, String>> vehicleRealTimeDStream) {
        JavaDStream<String> roadRealTimeLog = vehicleRealTimeDStream.map(new Function<ConsumerRecord<String, String>, String>() {
            @Override
            public String call(ConsumerRecord<String, String> cr) throws Exception {
                String key = cr.key();
                logger.info("key="+key);
                return cr.value();
            }
        });

        /**
         * 拿到车辆的信息了
         * 	car speed monitorId
         * <Monitor_id,Speed>
         */
        JavaPairDStream<String, Integer> mapToPair = roadRealTimeLog.mapToPair(new PairFunction<String, String, Integer>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, Integer> call(String log) throws Exception {
                String[] split = log.split("\t");
                //Tuple2(monitorId,speed)
                return new Tuple2<String, Integer>(split[1], Integer.parseInt(split[5]));
            }
        });

        JavaPairDStream<String, Tuple2<Integer, Integer>> monitorId2SpeedDStream =
                mapToPair.mapValues(new Function<Integer, Tuple2<Integer,Integer>>() {

                    /**
                     * <monitorId	<speed,1>>
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple2<Integer, Integer> call(Integer speed) throws Exception {
                        return new Tuple2<Integer, Integer>(speed, 1);
                    }
                });
        /**
         * 用优化的方式统计速度，返回的是tuple2(monitorId,(总速度，当前卡口通过的车辆总个数))
         */
        JavaPairDStream<String, Tuple2<Integer, Integer>> resultDStream =
                monitorId2SpeedDStream.reduceByKeyAndWindow(new Function2<Tuple2<Integer,Integer>, Tuple2<Integer,Integer>, Tuple2<Integer,Integer>>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Tuple2<Integer, Integer> v2) throws Exception {
                        return new Tuple2<Integer, Integer>(v1._1+v2._1, v1._2+v2._2);
                    }
                }, new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Tuple2<Integer, Integer> v2) throws Exception {
                        return new Tuple2<Integer, Integer>(v1._1 - v2._1,v2._2 - v2._2);
                    }
                }, Durations.minutes(5), Durations.seconds(5));
        /**
         * 使用reduceByKeyAndWindow  窗口大小是1分钟，如果你的application还有其他的功能点的话，另外一个功能点不能忍受这个长的延迟。权衡一下还是使用reduceByKeyAndWindow。
         */
        resultDStream.foreachRDD(new VoidFunction<JavaPairRDD<String, Tuple2<Integer, Integer>>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(JavaPairRDD<String, Tuple2<Integer, Integer>> rdd) throws Exception {
                //广播变量
                List<String> readFile = readFile("I:\\ControlCar.txt");
                JavaSparkContext sc = new JavaSparkContext(rdd.context());
                final Broadcast<List<String>> broadcast = sc.broadcast(readFile);
                final SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                rdd.foreachPartition(new VoidFunction<Iterator<Tuple2<String,Tuple2<Integer,Integer>>>>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void call(Iterator<Tuple2<String, Tuple2<Integer, Integer>>> iterator) throws Exception {
                        //使用广播变量
                        List<String> value = broadcast.value();
                        for(String s :value) {
                            System.out.println("bc value = "+s);
                        }
                        while (iterator.hasNext()) {
                            Tuple2<String, Tuple2<Integer, Integer>> tuple = iterator.next();
                            String monitor = tuple._1;
                            int speedCount = tuple._2._1;
                            int carCount = tuple._2._2;
                            logger.info("当前时间："+secondFormat.format(Calendar.getInstance().getTime())+
                                    "卡扣编号："+monitor + "车辆总数："+carCount + "速度总数：" + speedCount+" 平均速度："+(speedCount/carCount));
                        }
                    }
                });
            }
        });
    }
}
