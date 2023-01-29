package com.alex.spark;

import com.alex.bean.Task;
import com.alex.common.SpeedSortKey;
import com.alex.constants.Constants;
import com.alex.dao.TaskDao;
import com.alex.dao.factory.DaoFactory;
import com.alex.mock.MockData;
import com.alex.utils.ConfigUtil;
import com.alex.utils.DateUtil;
import com.alex.utils.ParamUtil;
import com.alex.utils.SparkUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.*;

/**
 * @author Alex_liu
 * @create 2023-01-29 19:09
 * @Description
 */
public class MonitorCarTrack {
    public static void main(String[] args) {

        /**
         * 判断应用程序是否在本地执行
         */
        JavaSparkContext sc = null;
        SparkSession spark = null;
        Boolean onLocal = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);

        if(onLocal){
            // 构建Spark运行时的环境参数
            SparkConf conf = new SparkConf().setAppName(Constants.SPARK_APP_NAME)
			.set("spark.sql.shuffle.partitions", "300")
			.set("spark.default.parallelism", "100")
			.set("spark.storage.memoryFraction", "0.5")
			.set("spark.shuffle.consolidateFiles", "true")
			.set("spark.shuffle.file.buffer", "64")
			.set("spark.shuffle.memoryFraction", "0.3")
			.set("spark.reducer.maxSizeInFlight", "96")
			.set("spark.shuffle.io.maxRetries", "60")
			.set("spark.shuffle.io.retryWait", "60")
			.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
			.registerKryoClasses(new Class[]{SpeedSortKey.class});
            /**
             * 设置spark运行时的master  根据配置文件来决定的
             */
            conf.setMaster("local");
            sc = new JavaSparkContext(conf);

            spark = SparkSession.builder().getOrCreate();
            /**
             * 基于本地测试生成模拟测试数据，如果在集群中运行的话，直接操作Hive中的表就可以
             * 本地模拟数据注册成一张临时表
             * monitor_flow_action	数据表：监控车流量所有数据
             * monitor_camera_info	标准表：卡扣对应摄像头标准表
             */
            MockData.mock(sc, spark);
        }else{
            System.out.println("++++++++++++++++++++++++++++++++++++++开启hive的支持");
            spark = SparkSession.builder().enableHiveSupport().getOrCreate();
            spark.sql("use traffic");
        }

//        SparkConf sparkConf = new SparkConf().setAppName(Constants.SPARK_APP_NAME);
//        SparkUtils.setMaster(sparkConf);
//
//        JavaSparkContext sc = new JavaSparkContext(sparkConf);
//        SparkSession spark = SparkUtils.getSQLContext(sc);
//
//        if (ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)) {
//            MockData.mock(sc, spark);
//        } else {
//            spark.sql("use traffic");
//        }

        long taskId = ParamUtil.getTaskIdFromArgs(args, Constants.SPARK_LOCAL_TASKID_MONITOR);
        if (taskId == 0L) {
            System.out.println("args is null");
            System.exit(-1);
        }

        TaskDao taskDao = DaoFactory.getTaskDao();
        Task task = taskDao.findTaskById(taskId);

        if (task == null) {
            System.exit(-1);
        }
        // 处理从task表中获得的参数
        JSONObject taskParamsJsonObject = JSONObject.parseObject(task.getTaskParams());

        // 获得row类型的RDD : 2018-01-23	0001	91631	京U16332	2018-01-23 00:27:58	202	5	06
        JavaRDD<Row> cameraRDD = SparkUtil.getCameraRDDByDateRange(spark, taskParamsJsonObject);
        cameraRDD.cache();

        // 获取map类型RDD: monitorID -> car       0001 : 京U16332
        JavaPairRDD<String, String> monitor2CarRDD = getMonitor2CarRDD(cameraRDD);

        // 过滤掉monitorId不等于0001的数据 0001 : 京U16332
        JavaPairRDD<String, String> filteredMonitor2CarRDD = getFilteredMonitor2CarRDD(monitor2CarRDD);

        // 去掉monitorId, 只保留car  京U16332
        JavaRDD<String> carRDD = getCarRDD(filteredMonitor2CarRDD);

        // 去重，然后获得car到集合中  [京U16332,京U16332...]
        List<String> carList = carRDD.distinct().collect();

        // 使得executor端能够获得carList
        final Broadcast<List<String>> carListBroadcast = sc.broadcast(carList);

        // print the reuslt
        // carList.forEach(System.out::println);

        // 开启另一条支线
        // 获得map类型RDD: car -> row   京U16332 : 2018-01-23	0001	91631	京U16332	2018-01-23 00:27:58	202	5	06
        JavaPairRDD<String, Row> car2RowRDD = getCar2RowRDD(cameraRDD);

        // 保留 0001卡扣下通过车辆的 信息
        JavaPairRDD<String, Row> filterCar2RowRDD = getFilteredCar2RowRDD(car2RowRDD, carListBroadcast);

        // car相同的放在一组中
        JavaPairRDD<String, Iterable<Row>> car2RowsRDD = filterCar2RowRDD.groupByKey();

        // 按时间排序，获得car -> monitor_ids   京U53611	: 0002-->0003-->0007-->0001-->0008-->0005-->0003-->0004-->0003
        JavaPairRDD<String, String> car2MonitorsRDD = getCar2MonitorsRDD(car2RowsRDD);

        // 打印结果
        car2MonitorsRDD.foreach(new VoidFunction<Tuple2<String, String>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(Tuple2<String, String> stringStringTuple2) throws Exception {
                String carId = stringStringTuple2._1;
                String track = stringStringTuple2._2;
                System.out.println(carId + "\t: " + track);
            }
        });
    }

    private static JavaPairRDD<String, String> getCar2MonitorsRDD(JavaPairRDD<String, Iterable<Row>> car2RowsRDD) {
        return car2RowsRDD.mapToPair(new PairFunction<Tuple2<String,Iterable<Row>>, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Tuple2<String, Iterable<Row>> stringIterableTuple2) throws Exception {
                String carId = stringIterableTuple2._1;
                Iterator<Row> iter = stringIterableTuple2._2.iterator();
                List<Row> rows = new ArrayList<>();
                while (iter.hasNext()) {
                    rows.add(iter.next());
                }

                Collections.sort(rows, new Comparator<Row>() {
                    @Override
                    public int compare(Row o1, Row o2) {
                        if (DateUtil.before(o1.getAs("action_time")+"", o2.getAs("action_time")+"")) {
                            return -1;
                        } else
                            return 1;
                    }
                });
                StringBuilder stringBuilder = new StringBuilder();
                for (Row row : rows) {
                    stringBuilder.append((String)row.getAs("monitor_id"));
                    stringBuilder.append("-->");
                }
                return new Tuple2<>(carId, stringBuilder.substring(0, stringBuilder.length() - 3));
            }
        });
    }

    private static JavaPairRDD<String, Row> getFilteredCar2RowRDD(JavaPairRDD<String, Row> car2RowRDD, final Broadcast<List<String>> carListBroadcast) {
        return car2RowRDD.filter(new Function<Tuple2<String, Row>, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(Tuple2<String, Row> v1) throws Exception {
                String carId = v1._1;
                List<String> carList = carListBroadcast.value();
                return carList.contains(carId);
            }
        });
    }

    private static JavaPairRDD<String, Row> getCar2RowRDD(JavaRDD<Row> cameraRDD) {
        return cameraRDD.mapToPair(new PairFunction<Row, String, Row>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, Row> call(Row row) throws Exception {
                return new Tuple2<>((String)row.getAs("car"), row);
            }
        });
    }

    private static JavaRDD<String> getCarRDD(JavaPairRDD<String, String> monitor2CarRDD) {
        return monitor2CarRDD.map(new Function<Tuple2<String, String>, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String call(Tuple2<String, String> v1) throws Exception {
                return v1._2;
            }
        });
    }

    private static JavaPairRDD<String,String> getFilteredMonitor2CarRDD(JavaPairRDD<String, String> monitor2CarRDD) {
        return monitor2CarRDD.filter(new Function<Tuple2<String, String>, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(Tuple2<String, String> v1) throws Exception {
                return v1._1.equals("0001");
            }
        });
    }

    private static JavaPairRDD<String, String> getMonitor2CarRDD(JavaRDD<Row> cameraRDD) {
        JavaPairRDD<String, String> monitor2CarRDD = cameraRDD.mapToPair(new PairFunction<Row, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Row row) throws Exception {
                return new Tuple2<>((String)row.getAs("monitor_id"), (String)row.getAs("car"));
            }
        });
        return monitor2CarRDD;
    }
}
