package com.alex.spark;

import com.alex.bean.Area;
import com.alex.bean.MonitoringState;
import com.alex.bean.Task;
import com.alex.bean.TopNVehicleMonitoring;
import com.alex.common.SelfDefineAccumulator;
import com.alex.constants.Constants;
import com.alex.dao.AreaDao;
import com.alex.dao.MonitoringDao;
import com.alex.dao.TaskDao;
import com.alex.dao.factory.DaoFactory;
import com.alex.common.SpeedSortKey;
import com.alex.mock.MockData;
import com.alex.utils.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.IteratorUtils;
import org.apache.spark.InternalAccumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.api.java.Optional;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.*;

/**
 * @author Alex_liu
 * @create 2023-01-29 17:35
 * @Description  卡扣流量监控模块
 *	          1.检测卡扣状态
 *            2.获取车流排名前N的卡扣号
 *            3.数据库保存累加器5个状态（正常卡扣数，异常卡扣数，正常摄像头数，异常摄像头数，异常摄像头的详细信息）
 *            4.topN 卡口的车流量具体信息存库
 *            5.获取高速通过的TOPN卡扣
 *            6.获取车辆高速通过的TOPN卡扣，每一个卡扣中车辆速度最快的前10名
 *            7.区域碰撞分析
 *            8.卡扣碰撞分析
 *
 *    任务提交:
 * ./spark-submit  --master spark://Flink03:7077
 * --class com.alex.spark.MonitorFlowAnalyze
 * --driver-class-path ../lib/mysql-connector-java-5.1.6.jar:../lib/fastjson-1.2.11.jar
 * --jars ../lib/mysql-connector-java-5.1.6.jar,../lib/fastjson-1.2.11.jar,../lib/transform.jar
 */
public class MonitorFlowAnalyze {
    public static void main(String[] args) {
        /**
         * 判断应用程序是否在本地执行
         */
        JavaSparkContext sc = null;
        SparkSession spark = null;
        Boolean onLocal = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);

        if(onLocal){
            // 构建Spark运行时的环境参数
            SparkConf conf = new SparkConf()
            .setAppName(Constants.SPARK_APP_NAME)
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
            //这里不会真正的创建SparkSession,而是根据前面这个SparkContext来获取封装SparkSession,因为不会创建存在两个SparkContext的。
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
            /**
             * "SELECT * FROM table1 join table2 ON (连接条件)"  如果某一个表小于20G 他会自动广播出去
             * 会将小于spark.sql.autoBroadcastJoinThreshold值（默认为10M）的表广播到executor节点，不走shuffle过程,更加高效。
             *
             * config("spark.sql.autoBroadcastJoinThreshold", "1048576000");  //单位：字节
             */
            spark = SparkSession.builder().appName(Constants.SPARK_APP_NAME).enableHiveSupport().getOrCreate();
            sc = new JavaSparkContext(spark.sparkContext());
            spark.sql("use traffic");
        }

        /**
         * 从配置文件my.properties中拿到spark.local.taskId.monitorFlow的taskId
         */
        long taskId = ParamUtil.getTaskIdFromArgs(args, Constants.SPARK_LOCAL_TASKID_MONITOR);
        if(taskId == 0L){
            System.out.println("args is null.....");
            return;
        }
        /**
         * 获取ITaskDAO的对象，通过taskId查询出来的数据封装到Task（自定义）对象
         */
        TaskDao taskDao = DaoFactory.getTaskDao();
        Task task = taskDao.findTaskById(taskId);

        if(task == null){
            return;
        }

        /**
         * task.getTaskParams()是一个json格式的字符串   封装到taskParamsJsonObject
         * 将 task_parm字符串转换成json格式数据。
         */
        JSONObject taskParamsJsonObject = JSONObject.parseObject(task.getTaskParams());

        /**
         * 通过params（json字符串）查询monitor_flow_action
         *
         * 获取指定日期内检测的monitor_flow_action中车流量数据，返回JavaRDD<Row>
         */
        JavaRDD<Row> cameraRDD = SparkUtil.getCameraRDDByDateRange(spark, taskParamsJsonObject);

        /**
         * 创建了一个自定义的累加器
         */
//		 Accumulator<String> monitorAndCameraStateAccumulator =
//				 sc.accumulator("", new MonitorAndCameraStateAccumulator());
        SelfDefineAccumulator monitorAndCameraStateAccumulator = new  SelfDefineAccumulator();
        spark.sparkContext().register(monitorAndCameraStateAccumulator,"SelfAccumulator");

        /**
         * 将row类型的RDD 转换成kv格式的RDD   k:monitor_id  v:row
         */
        JavaPairRDD<String, Row> monitor2DetailRDD = getMonitor2DetailRDD(cameraRDD);
        /**
         * monitor2DetailRDD进行了持久化
         */
        monitor2DetailRDD = monitor2DetailRDD.cache();
        /**
         * 按照卡扣号分组，对应的数据是：每个卡扣号(monitor)对应的Row信息
         * 由于一共有9个卡扣号，这里groupByKey后一共有9组数据。
         */
        JavaPairRDD<String, Iterable<Row>> monitorId2RowsRDD = monitor2DetailRDD.groupByKey();

        /**
         * 遍历分组后的RDD，拼接字符串
         * 数据中一共就有9个monitorId信息，那么聚合之后的信息也是9条
         * monitor_id=|cameraIds=|area_id=|camera_count=|carCount=
         * 例如:
         * ("0005","monitorId=0005|camearIds=09200,03243,02435,03232|cameraCount=4|carCount=100")
         *
         */
        JavaPairRDD<String, String> aggregateMonitorId2DetailRDD = aggreagteByMonitor(monitorId2RowsRDD);

        /**
         * 检测卡扣状态
         * carCount2MonitorRDD
         * K:car_count V:monitor_id
         * RDD(卡扣对应车流量总数,对应的卡扣号)
         */
        JavaPairRDD<Integer, String> carCount2MonitorRDD =
                checkMonitorState(sc,spark,aggregateMonitorId2DetailRDD,taskId,taskParamsJsonObject,monitorAndCameraStateAccumulator);
        /**
         * action 类算子触发以上操作
         *
         */
        carCount2MonitorRDD.count();

        /**
         * 往数据库表  monitor_state 中保存 累加器累加的五个状态
         */
        saveMonitorState(taskId,monitorAndCameraStateAccumulator);

        /************车辆轨迹**cameraRDD************/
        //从所有数据中找出卡扣 0001 下的车辆
        List<String> cars = cameraRDD.filter(new Function<Row, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(Row row)  {
                return "0001".equals(row.getAs("monitor_id")+"");
            }
        }).map(new Function<Row, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String call(Row row)  {
                return row.getAs("car")+"";
            }
        }).distinct().take(20);

        final Broadcast<List<String>> bc = sc.broadcast(cars);

        cameraRDD.mapToPair(new PairFunction<Row, String, Row>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Row> call(Row row)  {
                return new Tuple2<>(row.getAs("car")+"", row);
            }
        }).filter(new Function<Tuple2<String,Row>, Boolean>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean call(Tuple2<String, Row> tuple)  {
                return bc.value().contains(tuple._1);
            }
        }).groupByKey().foreach(new VoidFunction<Tuple2<String,Iterable<Row>>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(Tuple2<String, Iterable<Row>> tp)  {
                String car = tp._1;
                @SuppressWarnings("unchecked")
                List<Row> rowList = IteratorUtils.toList(tp._2.iterator());
                Collections.sort(rowList,new Comparator<Row>() {
                    @Override
                    public int compare(Row r1, Row r2) {
                        String r1Time = r1.getAs("action_time")+"";
                        String r2Time = r2.getAs("action_time")+"";
                        return DateUtil.before(r1Time, r2Time)?-1:1;
                    }
                });
                String carTracker = "";
                for(Row row:rowList) {
                    carTracker += "-->"+row.getAs("monitor_id")+"_"+row.getAs("action_time");
                }
                System.out.println("car = "+car +",carTracker = "+carTracker.substring(3));
            }
        });

        /**
         * 获取车流排名前N的卡扣号
         * 并放入数据库表  topn_monitor_car_count 中
         * return  KV格式的RDD  K：monitor_id V:monitor_id
         * 返回的是topN的(monitor_id,monitor_id)
         */
        JavaPairRDD<String, String> topNMonitor2CarFlow =
                getTopNMonitorCarFlow(sc,taskId,taskParamsJsonObject,carCount2MonitorRDD);

        /**
         * 获取topN 卡口的车流量具体信息，存入数据库表 topn_monitor_detail_info 中
         */
        getTopNDetails(taskId,topNMonitor2CarFlow,monitor2DetailRDD);

        /**
         * 获取车辆高速通过的TOPN卡扣
         */
        List<String> top5MonitorIds = speedTopNMonitor(monitorId2RowsRDD);
        for (String monitorId : top5MonitorIds) {
            System.out.println("车辆经常高速通过的卡扣	monitorId:"+monitorId);
        }
        /**
         * 获取车辆高速通过的TOPN卡扣，每一个卡扣中车辆速度最快的前10名，并存入数据库表 top10_speed_detail 中
         */
        getMonitorDetails(sc,taskId,top5MonitorIds,monitor2DetailRDD);

        /**
         * 区域碰撞分析,直接打印显示出来。
         * "01","02" 指的是两个区域
         */

        CarPeng(spark,taskParamsJsonObject,"01","02");

        /**
         * 卡扣碰撞分析，直接打印结果
         *
         */
        areaCarPeng(spark,taskParamsJsonObject);
        System.out.println("******All is finished*******");
        sc.close();
    }


    private static void lineCar(JavaSparkContext javaSparkContext, final String FiltermonitorId, JavaRDD<Row> cameraRDD) {
        System.out.println("******start lineCar*******");
        JavaRDD<String> carsInFilterMonitorIdRDD = cameraRDD.mapToPair(new PairFunction<Row, String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, String> call(Row row)  {
                return new Tuple2<String, String>(row.getString(1), row.getString(3));
            }
        }).filter(new Function<Tuple2<String, String>, Boolean>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean call(Tuple2<String, String> v1)  {
                if (FiltermonitorId.equals(v1._1)) {
                    return true;
                } else {
                    System.out.println("FiltermonitorId--not--:" + v1);
                    return false;
                }
            }
        }).map(new Function<Tuple2<String, String>, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public String call(Tuple2<String, String> v1)  {
                return v1._2;
            }
        }).distinct();
        final List<String> carsInFilterMonitorIdList = carsInFilterMonitorIdRDD.collect();
        final Broadcast<List<String>> carsInFilterMonitorIdListBroadcast = javaSparkContext.broadcast(carsInFilterMonitorIdList);
        System.out.println(carsInFilterMonitorIdListBroadcast.value());
        JavaPairRDD<String, Iterable<String>> stringIterableJavaPairRDD = cameraRDD.mapToPair(new PairFunction<Row, String, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, String> call(Row row)  {
                return new Tuple2(row.getString(3), row.getString(4) + "#" + row.getString(1));
            }
        }).filter(new Function<Tuple2<String, String>, Boolean>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Boolean call(Tuple2<String, String> v1)  {
                return carsInFilterMonitorIdListBroadcast.value().contains(v1._1);
            }
        }).groupByKey();
        stringIterableJavaPairRDD.foreach(new VoidFunction<Tuple2<String, Iterable<String>>>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            //2018-02-28 21:45:28#0004
            @Override
            public void call(Tuple2<String, Iterable<String>> stringIterableTuple2)  {
//	                System.out.println("this Car is :" + stringIterableTuple2._1);
//	                System.out.println("Source :"+stringIterableTuple2);//原数据

//	                System.out.println("使用arrlist排序");
                Iterator<String> timeIdInCar = stringIterableTuple2._2.iterator();
                ArrayList<String> arrayList =new ArrayList<>();
                while (timeIdInCar.hasNext()) {
                    arrayList.add(timeIdInCar.next());
                }
                Collections.sort(arrayList);
                StringBuilder sb=new StringBuilder();
                for (String s:arrayList) {
                    String[] temp = s.split("#");
                    sb.append("-->").append(temp[1]);
                }
                System.out.println("arrayList--sort:" + sb.substring(3));//排序后数据

                System.out.println("使用sortedMap排序");
                Iterator<String> timeIdInCar2 = stringIterableTuple2._2.iterator();
                SortedMap<String, String> sortedMap = new TreeMap<String, String>();
                while (timeIdInCar2.hasNext()) {
                    String[] temp = timeIdInCar2.next().split("#");
                    sortedMap.put(temp[0], temp[1]);
                }
                System.out.println("sortedMap--sort:" + sortedMap.values());//排序后数据
            }
        });
        System.out.println("******finish lineCar*******");
    }

    /**
     * 卡扣碰撞分析
     * 假设数据如下：
     * area1卡扣:["0000", "0001", "0002", "0003"]
     * area2卡扣:["0004", "0005", "0006", "0007"]
     */
    private static void areaCarPeng(SparkSession spark,JSONObject taskParamsJsonObject){
        List<String> monitorIds1 = Arrays.asList("0000", "0001", "0002", "0003");
        List<String> monitorIds2 = Arrays.asList("0004", "0005", "0006", "0007");
        // 通过两堆卡扣号，分别取数据库（本地模拟的两张表）中查询数据
        JavaRDD<Row> areaRDD1 = getAreaRDDByMonitorIds(spark, taskParamsJsonObject, monitorIds1);
        JavaRDD<Row> areaRDD2 = getAreaRDDByMonitorIds(spark, taskParamsJsonObject, monitorIds2);

        JavaRDD<String> area1Cars = areaRDD1.map(new Function<Row, String>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public String call(Row row)  {
                return row.getAs("car")+"";
            }
        }).distinct();
        JavaRDD<String> area2Cars = areaRDD2.map(new Function<Row, String>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public String call(Row row)  {
                return row.getAs("car")+"";
            }
        }).distinct();


        JavaRDD<String> intersection = area1Cars.intersection(area2Cars);
        intersection.foreach(new VoidFunction<String>() {

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void call(String car)  {
                System.out.println("area1 与 area2 共同车辆="+car);
            }
        });
    }
    
    /**
     * 区域碰撞分析：两个区域共同出现的车辆
     * @param area1
     * @param area2
     */
    private static void CarPeng(SparkSession spark,JSONObject taskParamsJsonObject,String area1,String area2){
        //得到01区域的数据放入rdd01
        JavaRDD<Row> cameraRDD01 = SparkUtil.getCameraRDDByDateRangeAndArea(spark, taskParamsJsonObject,area1);
        //得到02区域的数据放入rdd02
        JavaRDD<Row> cameraRDD02 = SparkUtil.getCameraRDDByDateRangeAndArea(spark, taskParamsJsonObject,area2);
        JavaRDD<String> distinct1 = cameraRDD01.map(new Function<Row, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String call(Row row)  {
                return row.getAs("car");
            }
        }).distinct();
        JavaRDD<String> distinct2 = cameraRDD02.map(new Function<Row, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String call(Row row)  {
                return row.getAs("car");
            }
        }).distinct();
        distinct1.intersection(distinct2).foreach(new VoidFunction<String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(String car)  {
                System.out.println("01 ，02 区域同时出现的car ***** "+car);
            }
        });
    }

    private static JavaPairRDD<String, Object> getCar2DetailRDD(JavaRDD<Row> areaRowRDD1) {
        return areaRowRDD1.mapToPair(new PairFunction<Row, String, Object>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, Object> call(Row row)  {
                return new Tuple2<>(row.getString(3),null);
            }
        });
    }
    
    private static JavaRDD<Row> getAreaRDDByMonitorIds(SparkSession spark,JSONObject taskParamsJsonObject, List<String> monitorId1) {
        String startTime = ParamUtil.getParam(taskParamsJsonObject, Constants.PARAM_START_DATE);
        String endTime = ParamUtil.getParam(taskParamsJsonObject, Constants.PARAM_END_DATE);
        String sql = "SELECT * "
                + "FROM monitor_flow_action"
                + " WHERE date >='" + startTime + "' "
                + " AND date <= '" + endTime+ "' "
                + " AND monitor_id in (";

        for(int i = 0 ; i < monitorId1.size() ; i++){
            sql += "'"+monitorId1.get(i) + "'";

            if( i  < monitorId1.size() - 1 ){
                sql += ",";
            }
        }
        sql += ")";
        return spark.sql(sql).javaRDD();
    }

    private static JavaPairRDD<String, String> addAreaNameAggregateMonitorId2DetailRDD(JavaPairRDD<String, String> aggregateMonitorId2DetailRDD, SparkSession spark) {
        /**
         * 从数据库中查询出来areaName 与 areaId
         */
        Boolean local = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);

        String url = local?ConfigUtil.getProperty(Constants.JDBC_URL):ConfigUtil.getProperty(Constants.JDBC_URL_PROD);
        String user = local?ConfigUtil.getProperty(Constants.JDBC_USER):ConfigUtil.getProperty(Constants.JDBC_USER_PROD);
        String password = local?ConfigUtil.getProperty(Constants.JDBC_PASSWORD):ConfigUtil.getProperty(Constants.JDBC_PASSWORD_PROD);
        Map<String, String> options = new HashMap<>();
        options.put("url", url);
        options.put("user", user);
        options.put("password", password);
        options.put("dbtable", "area_info");

        Dataset<Row> areaInfoDF = spark.read().format("jdbc").options(options).load();
        JavaPairRDD<String, String> areaId2AreaNameRDD = areaInfoDF.javaRDD().mapToPair(new PairFunction<Row, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Row row)  {
                return new Tuple2<>(row.getString(0),row.getString(1));
            }
        });

        JavaPairRDD<String, String> areaId2DetailRDD = aggregateMonitorId2DetailRDD.mapToPair(new PairFunction<Tuple2<String,String>, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Tuple2<String, String> tuple)  {
                String infos = tuple._2;
                String areaId = StringUtil.getFieldFromConcatString(infos, "\\|", Constants.FIELD_AREA_ID);
                return new Tuple2<>(areaId,infos);
            }
        });
        return areaId2AreaNameRDD.join(areaId2DetailRDD).mapToPair(new PairFunction<Tuple2<String,Tuple2<String,String>>, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Tuple2<String, Tuple2<String, String>> tuple)  {
                String areaId = tuple._1;
                String areaName = tuple._2._1;
                String infos = tuple._2._2;
                infos += "|"+Constants.FIELD_AREA_NAME+"="+areaName;
                String monitorId = StringUtil.getFieldFromConcatString(infos, "\\|", Constants.FIELD_MONITOR_ID);
                return new Tuple2<>(monitorId,infos);
            }
        });
    }
    
    private static JavaPairRDD<String, String> addAreaNameByBroadCast2AggreageByMnonitor(JavaSparkContext sc, JavaPairRDD<String, String> aggregateMonitorId2DetailRDD) {
        //从数据中查询出来  区域信息
        Map<String, String> areaMap = getAreaInfosFromDB();
        final Broadcast<Map<String, String>> broadcastAreaMap = sc.broadcast(areaMap);
        aggregateMonitorId2DetailRDD.mapToPair(new PairFunction<Tuple2<String,String>, String,String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Tuple2<String, String> tuple)  {
                /**
                 * 从广播变量中取出来区域信息  k:area_id  v:area_name
                 */
                Map<String, String> areaMap = broadcastAreaMap.value();
                String monitorId = tuple._1;
                String aggregateInfos = tuple._2;
                String area_id = StringUtil.getFieldFromConcatString(aggregateInfos,"\\|",Constants.FIELD_AREA_ID);
                String area_name = areaMap.get(area_id);
                aggregateInfos += "|"+Constants.FIELD_AREA_NAME+"="+area_name;
                return new Tuple2<String, String>(monitorId,aggregateInfos);
            }
        });
        return null;
    }

    private static Map<String, String> getAreaInfosFromDB() {
        AreaDao areaDao = DaoFactory.getAreaDao();

        List<Area> findAreaInfo = areaDao.findAreaInfo();

        Map<String, String> areaMap = new HashMap<>();
        for (Area area : findAreaInfo) {
            areaMap.put(area.getAreaId(), area.getAreaName());
        }
        return areaMap;
    }

    /**
     * fulAggreageByMonitorRDD    key:monitorid  value:包含areaName信息
     * @param fulAggreageByMonitorRDD
     * @param taskParamsJsonObject
     * @return
     */
    @SuppressWarnings("resource")
    private static JavaPairRDD<String, String> filterRDDByAreaName(JavaPairRDD<String, String> fulAggreageByMonitorRDD, JSONObject taskParamsJsonObject) {
        
        /**
         * area_name的获取是在Driver段获取 的
         * area_name的使用时在Executor端  可以将area_name放入到广播变量中，然后在Executor中直接从广播变量中获取相应的参数值
         */
        String area_name = ParamUtil.getParam(taskParamsJsonObject, Constants.FIELD_AREA_NAME);
        /**
         * 从RDD中获取SparkContext
         */
        SparkContext sc = fulAggreageByMonitorRDD.context();
        JavaSparkContext jsc = new JavaSparkContext(sc);
        final Broadcast<String> areaNameBroadcast = jsc.broadcast(area_name);
        return fulAggreageByMonitorRDD.filter(new Function<Tuple2<String,String>, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(Tuple2<String, String> tuple)  {
                String aggregateInfos = tuple._2;
                String area_name = areaNameBroadcast.value();
                String factAreaName = StringUtil.getFieldFromConcatString(aggregateInfos, "\\|", Constants.FIELD_AREA_NAME);
                return area_name.equals(factAreaName);
            }
        });
    }

    /**
     * 补全区域名
     * @param spark
     * @param aggregateMonitorId2DetailRDD
     * @return
     */
    private static JavaPairRDD<String, String> addAreaName2AggreageByMnonitor(SparkSession spark, JavaPairRDD<String, String> aggregateMonitorId2DetailRDD) {
        /***
         * 准备连接数据的配置信息
         */
        Boolean local = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);
        String	url = local?ConfigUtil.getProperty(Constants.JDBC_URL):ConfigUtil.getProperty(Constants.JDBC_URL_PROD);
        String	user = local?ConfigUtil.getProperty(Constants.JDBC_USER):ConfigUtil.getProperty(Constants.JDBC_USER_PROD);
        String	password = local?ConfigUtil.getProperty(Constants.JDBC_PASSWORD):ConfigUtil.getProperty(Constants.JDBC_PASSWORD_PROD);

        Map<String, String> props = new HashMap<>();
        props.put("url", url);
        props.put("user", user);
        props.put("password", password);
        props.put("dbtable", "area_info");

        /**
         * 将mysql中的area_info表加载到area_Info_DF里面
         */
        Dataset<Row> area_Info_DF = spark.read().format("jdbc").options(props).load();
        /**
         * 因为要与我们传入的aggregateMonitorId2DetailRDD进行join
         * join连接的连接的字段是area_id
         */
        JavaRDD<Row> areaInfosRDD = area_Info_DF.javaRDD();
        JavaPairRDD<String, String> areaId2AreaNameRDD = areaInfosRDD.mapToPair(new PairFunction<Row, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Row row)  {
                String area_id = row.getString(0);
                String area_name = row.getString(1);
                return new Tuple2<String, String>(area_id,area_name);
            }
        });

        JavaPairRDD<String, String> areaId2AggregateInfosRDD = aggregateMonitorId2DetailRDD.mapToPair(new PairFunction<Tuple2<String,String>, String, String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, String> call(Tuple2<String, String> tuple)  {
                String aggregateInfos = tuple._2;
                String area_Id = StringUtil.getFieldFromConcatString(aggregateInfos, "\\|", Constants.FIELD_AREA_ID);
                return new Tuple2<String, String>(area_Id,aggregateInfos);
            }
        });

        /**
         * 使用广播变量来代替join
         * 	join会产生shuffle（有shuffle） = filter + 广播变量 （就不会产生shuffle）
         */
        return areaId2AreaNameRDD.join(areaId2AggregateInfosRDD).mapToPair(new PairFunction<Tuple2<String,Tuple2<String,String>>, String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, String> call(Tuple2<String, Tuple2<String, String>> tuple)  {
                String area_name = tuple._2._1;
                String aggregateInfos = tuple._2._2;
                aggregateInfos += "|"+Constants.FIELD_AREA_NAME + "=" + area_name;
                String monitor_Id = StringUtil.getFieldFromConcatString(aggregateInfos, "\\|", Constants.FIELD_MONITOR_ID);
                return new Tuple2<String, String>(monitor_Id,aggregateInfos);
            }
        });
    }

    /**
     * 获取车辆经常高速通过的TOPN卡扣，每一个卡扣中车辆速度最快的前10名，并存入数据库表 top10_speed_detail 中
     * @param sc
     * @param taskId
     * @param top10MonitorIds
     * @param monitor2DetailRDD
     */
    private static void getMonitorDetails(JavaSparkContext sc , final long taskId,List<String> top5MonitorIds, JavaPairRDD<String, Row> monitor2DetailRDD) {

        /**
         * top5MonitorIds这个集合里面都是monitor_id
         */
        final Broadcast<List<String>> top5MonitorIdsBroadcast = sc.broadcast(top5MonitorIds);

        /**
         * 我们想获取每一个卡扣的详细信息，就是从monitor2DetailRDD中取出来包含在top10MonitorIds集合的卡扣的信息
         */
        monitor2DetailRDD.filter(new Function<Tuple2<String,Row>, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(Tuple2<String, Row> tuple)  {
                String monitorIds = tuple._1;
                List<String> list = top5MonitorIdsBroadcast.value();
                return list.contains(monitorIds);
            }
        }).groupByKey().foreach(new VoidFunction<Tuple2<String,Iterable<Row>>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(Tuple2<String, Iterable<Row>> tuple)  {
                String monitor_id = tuple._1;
                Iterator<Row> rowsIterator = tuple._2.iterator();
                Row[] top10Cars = new Row[10];
                while (rowsIterator.hasNext()) {
                    Row row = rowsIterator.next();
                    long speed = Long.valueOf(row.getString(5));
                    for(int i = 0; i < top10Cars.length; i++) {
                        if(top10Cars[i] == null) {
                            top10Cars[i] = row;
                            break;
                        } else {
                            long _speed = Long.valueOf(top10Cars[i].getString(5));
                            if(speed > _speed) {
                                for(int j = 9; j > i; j--) {
                                    top10Cars[j] = top10Cars[j - 1];
                                }
                                top10Cars[i] = row;
                                break;
                            }
                        }
                    }
                }

                /**
                 * 将车辆通过速度最快的前N个卡扣中每个卡扣通过的车辆的速度最快的前10名存入数据库表 top10_speed_detail中
                 */
                List<TopNVehicleMonitoring> topNMonitorDetailInfos = new ArrayList<>();
                for (Row row : top10Cars) {
                    topNMonitorDetailInfos.add(new TopNVehicleMonitoring(taskId, row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getString(4), row.getString(5), row.getString(6)));
                }
                MonitoringDao monitorDao = DaoFactory.getMonitorDAO();
                monitorDao.insertBatchMonitoringDetail(topNMonitorDetailInfos);
            }
        });
    }

    /**
     * 获取经常高速通过的TOPN卡扣 , 返回车辆经常高速通过的卡扣List
     *
     * 1、每一辆车都有speed    按照速度划分是否是高速 中速 普通 低速
     * 2、每一辆车的车速都在一个车速段     对每一个卡扣进行聚合   拿到高速通过 中速通过  普通  低速通过的车辆各是多少辆
     * 3、四次排序   先按照高速通过车辆数   中速通过车辆数   普通通过车辆数   低速通过车辆数
     * @param groupByMonitorId ---- (monitorId ,Iterable[Row])
     * @return List<MonitorId> 返回车辆经常高速通过的卡扣List
     */
    private static List<String> speedTopNMonitor(JavaPairRDD<String, Iterable<Row>> groupByMonitorId) {
        /**
         * key:自定义的类  value：卡扣ID
         */
        JavaPairRDD<SpeedSortKey, String> speedSortKey2MonitorId =
                groupByMonitorId.mapToPair(new PairFunction<Tuple2<String,Iterable<Row>>, SpeedSortKey,String>() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple2<SpeedSortKey, String> call(Tuple2<String, Iterable<Row>> tuple)  {
                        String monitorId = tuple._1;
                        Iterator<Row> speedIterator = tuple._2.iterator();

                        /**
                         * 这四个遍历 来统计这个卡扣下 高速 中速 正常 以及低速通过的车辆数
                         */
                        long lowSpeed = 0;
                        long normalSpeed = 0;
                        long mediumSpeed = 0;
                        long highSpeed = 0;

                        while(speedIterator.hasNext()){
                            int speed = StringUtil.convertStringtoInt(speedIterator.next().getString(5));
                            if(speed >= 0 && speed < 60){
                                lowSpeed ++;
                            }else if (speed >= 60 && speed < 90) {
                                normalSpeed ++;
                            }else if (speed >= 90 && speed < 120) {
                                mediumSpeed ++;
                            }else if (speed >= 120) {
                                highSpeed ++;
                            }
                        }
                        SpeedSortKey speedSortKey = new SpeedSortKey(lowSpeed,normalSpeed,mediumSpeed,highSpeed);
                        return new Tuple2<>(speedSortKey, monitorId);
                    }
                });
        /**
         * key:自定义的类  value：卡扣ID
         */
        JavaPairRDD<SpeedSortKey, String> sortBySpeedCount = speedSortKey2MonitorId.sortByKey(false);
        /**
         * 硬编码问题
         * 取出前5个经常速度高的卡扣
         */
        List<Tuple2<SpeedSortKey, String>> take = sortBySpeedCount.take(5);

        List<String> monitorIds = new ArrayList<>();
        for (Tuple2<SpeedSortKey, String> tuple : take) {
            monitorIds.add(tuple._2);
            System.out.println("monitor_id = "+tuple._2+"-----"+tuple._1);
        }
        return monitorIds;
    }


    /**
     * 按照monitor_id进行聚合
     * @param monitorId2Detail
     * @return ("monitorId","monitorId=xxx|areaId=xxx|cameraIds=xxx|cameraCount=xxx|carCount=xxx")
     * ("0005","monitorId=0005|areaId=02|camearIds=09200,03243,02435,03232|cameraCount=4|carCount=100")
     * 假设其中一条数据是以上这条数据，那么说明在这个0005卡扣下有4个camera,那么这个卡扣一共通过了100辆车信息.
     */
    private static JavaPairRDD<String, String> aggreagteByMonitor(JavaPairRDD<String, Iterable<Row>>  monitorId2RowRDD) {
        /**
         * <monitor_id,List<Row> 集合里面的一个row记录代表的是camera的信息，row也可以说是代表的一辆车的信息
         *
         * 一个monitor_id对应一条记录
         * 为什么使用mapToPair来遍历数据，因为我们要操作的返回值是每一个monitorid 所对应的详细信息
         */
        JavaPairRDD<String, String> monitorId2CameraCountRDD = monitorId2RowRDD.mapToPair(
                new PairFunction<Tuple2<String,Iterable<Row>>,String, String>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<String, String> call(Tuple2<String, Iterable<Row>> tuple)  {
                        String monitorId = tuple._1;
                        Iterator<Row> rowIterator = tuple._2.iterator();

                        List<String> list = new ArrayList<>();//同一个monitorId下，对应的所有的不同的cameraId,list.count方便知道此monitor下对应多少个cameraId

                        StringBuilder tmpInfos = new StringBuilder();//同一个monitorId下，对应的所有的不同的camearId信息

                        int count = 0;//统计车辆数的count
                        /**
                         * 这个while循环  代表的是当前的这个卡扣一共经过了多少辆车，   一辆车的信息就是一个row
                         */
                        while(rowIterator.hasNext()){
                            Row row = rowIterator.next();
//					areaId = row.getString(7);
                            String cameraId = row.getString(2);
                            if(!list.contains(cameraId)){
                                list.add(cameraId);
                            }
                            //针对同一个卡扣 monitor，append不同的cameraId信息
                            if(!tmpInfos.toString().contains(cameraId)){
                                tmpInfos.append(","+cameraId);
                            }
                            //这里的count就代表的车辆数，一个row一辆车
                            count++;
                        }

                        /**
                         * camera_count
                         */
                        int cameraCount = list.size();
                        //monitorId=0001|cameraIds=00001,00002,00003|cameraCount=3|carCount=100
                        String infos =  Constants.FIELD_MONITOR_ID+"="+monitorId+"|"
                                +Constants.FIELD_CAMERA_IDS+"="+tmpInfos.toString().substring(1)+"|"
                                +Constants.FIELD_CAMERA_COUNT+"="+cameraCount+"|"
                                +Constants.FIELD_CAR_COUNT+"="+count;
                        return new Tuple2<String, String>(monitorId, infos);
                    }
                });
        //<monitor_id,camera_infos(ids,cameracount,carCount)>
        return monitorId2CameraCountRDD;
    }

    /**
     * 往数据库中保存 累加器累加的五个状态
     * @param taskId
     * @param monitorAndCameraStateAccumulator
     */
    private static void saveMonitorState(Long taskId,SelfDefineAccumulator monitorAndCameraStateAccumulator) {
        /**
         * 累加器中值能在Executor段读取吗？
         * 		不能
         * 这里的读取时在Driver中进行的
         */
        String accumulatorVal = monitorAndCameraStateAccumulator.value();
        String normalMonitorCount = StringUtil.getFieldFromConcatString(accumulatorVal, "\\|", Constants.FIELD_NORMAL_MONITOR_COUNT);
        String normalCameraCount = StringUtil.getFieldFromConcatString(accumulatorVal, "\\|", Constants.FIELD_NORMAL_CAMERA_COUNT);
        String abnormalMonitorCount = StringUtil.getFieldFromConcatString(accumulatorVal, "\\|", Constants.FIELD_ABNORMAL_MONITOR_COUNT);
        String abnormalCameraCount = StringUtil.getFieldFromConcatString(accumulatorVal, "\\|", Constants.FIELD_ABNORMAL_CAMERA_COUNT);
        String abnormalMonitorCameraInfos = StringUtil.getFieldFromConcatString(accumulatorVal, "\\|", Constants.FIELD_ABNORMAL_MONITOR_CAMERA_INFOS);

        /**
         * 这里面只有一条记录
         */
        MonitoringState monitorState = new MonitoringState(taskId, normalMonitorCount, normalCameraCount, abnormalMonitorCount, abnormalCameraCount, abnormalMonitorCameraInfos);

        /**
         * 向数据库表monitor_state中添加累加器累计的各个值
         */
        MonitoringDao monitoringDao = DaoFactory.getMonitorDAO();
        monitoringDao.insertMonitoringState(monitorState);
    }

    /**
     * 将RDD转换成K,V格式的RDD
     * @param cameraRDD
     * @return
     */
    private static JavaPairRDD<String, Row> getMonitor2DetailRDD(JavaRDD<Row> cameraRDD) {
        JavaPairRDD<String, Row> monitorId2Detail = cameraRDD.mapToPair(new PairFunction<Row, String, Row>() {

            /**
             * row.getString(1) 是得到monitor_id 。
             */
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, Row> call(Row row)  {
                return new Tuple2<String, Row>(row.getString(1),row);
            }
        });
        return monitorId2Detail;
    }

    /**
     * 获取topN 卡口的车流量具体信息，存入数据库表 topn_monitor_detail_info 中
     * @param taskId
     * @param topNMonitor2CarFlow ---- (monitorId,monitorId)
     * @param monitor2DetailRDD ---- (monitorId,Row)
     */
    private static void getTopNDetails(
            final long taskId,JavaPairRDD<String, String> topNMonitor2CarFlow,
            JavaPairRDD<String, Row> monitor2DetailRDD) {

        /**
         * 获取车流量排名前N的卡口的详细信息   可以看一下是在什么时间段内卡口流量暴增的
         * 优化点：
         * 因为topNMonitor2CarFlow 里面有只有5条数据，可以将这五条数据封装到广播变量中，然后遍历monitor2DetailRDD ，每遍历一条数据与广播变量中的值作比对。
         */
        topNMonitor2CarFlow.join(monitor2DetailRDD).mapToPair(
                new PairFunction<Tuple2<String,Tuple2<String,Row>>, String, Row>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<String, Row> call(Tuple2<String, Tuple2<String, Row>> t)  {
                        return new Tuple2<String, Row>(t._1, t._2._2);
                    }
                }).foreachPartition(new VoidFunction<Iterator<Tuple2<String,Row>>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(Iterator<Tuple2<String, Row>> t)  {
                List<TopNVehicleMonitoring> monitorDetailInfos = new ArrayList<>();
                while (t.hasNext()) {
                    Tuple2<String, Row> tuple = t.next();
                    Row row = tuple._2;
                    TopNVehicleMonitoring m = new TopNVehicleMonitoring(taskId, row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getString(4), row.getString(5), row.getString(6));
                    monitorDetailInfos.add(m);
                }
                /**
                 * 将topN的卡扣车流量明细数据 存入topn_monitor_detail_info 表中
                 */
                MonitoringDao monitorDao = DaoFactory.getMonitorDAO();
                monitorDao.insertBatchMonitoringDetail(monitorDetailInfos);
            }
        });

        /********************************使用广播变量来实现************************************/
		JavaSparkContext jsc = new JavaSparkContext(topNMonitor2CarFlow.context());
		//将topNMonitor2CarFlow（只有5条数据）转成非K,V格式的数据，便于广播出去
		JavaRDD<String> topNMonitorCarFlow = topNMonitor2CarFlow.map(new Function<Tuple2<String,String>, String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String call(Tuple2<String, String> tuple)  {
				return tuple._1;
			}
		});
		List<String> topNMonitorIds = topNMonitorCarFlow.collect();
		final Broadcast<List<String>> broadcast_topNMonitorIds = jsc.broadcast(topNMonitorIds);
		JavaPairRDD<String, Row> filterTopNMonitor2CarFlow = monitor2DetailRDD.filter(new Function<Tuple2<String,Row>, Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean call(Tuple2<String, Row> monitorTuple)  {

				return broadcast_topNMonitorIds.value().contains(monitorTuple._1);
			}
		});
		filterTopNMonitor2CarFlow.foreachPartition(new VoidFunction<Iterator<Tuple2<String,Row>>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(Iterator<Tuple2<String, Row>> t)
					 {
				List<TopNVehicleMonitoring> monitorDetailInfos = new ArrayList<>();
				while(t.hasNext()){
					Tuple2<String, Row> tuple = t.next();
					Row row = tuple._2;
                    TopNVehicleMonitoring m = new TopNVehicleMonitoring(taskId, row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getString(4), row.getString(5), row.getString(6));
					monitorDetailInfos.add(m);
				}
				/**
				 * 将topN的卡扣车流量明细数据 存入topn_monitor_detail_info 表中
				 */
				MonitoringDao monitorDao = DaoFactory.getMonitorDAO();
				monitorDao.insertBatchMonitoringDetail(monitorDetailInfos);
			}
		});
    }

    /**
     * 获取卡口流量的前N名，并且持久化到数据库中
     * N是在数据库条件中取值
     * @param taskId
     * @param taskParamsJsonObject
     * @param carCount2MonitorId----RDD(卡扣对应的车流量总数,对应的卡扣号)
     */
    private static JavaPairRDD<String, String> getTopNMonitorCarFlow(
            JavaSparkContext sc,
            long taskId,JSONObject taskParamsJsonObject,
            JavaPairRDD<Integer, String> carCount2MonitorId) {
        /**
         * 获取车流量排名前N的卡口信息
         * 有什么作用？ 当某一个卡口的流量这几天突然暴增和往常的流量不相符，交管部门应该找一下原因，是什么问题导致的，应该到现场去疏导车辆。
         */
        int topNumFromParams = Integer.parseInt(ParamUtil.getParam(taskParamsJsonObject,Constants.FIELD_TOP_NUM));

        /**
         * carCount2MonitorId <carCount,monitor_id>
         */
        List<Tuple2<Integer, String>> topNCarCount = carCount2MonitorId.sortByKey(false).take(topNumFromParams);

        //封装到对象中
        List<TopNVehicleMonitoring> topNMonitor2VehicleCounts = new ArrayList<>();
        for (Tuple2<Integer, String> tuple : topNCarCount) {
            TopNVehicleMonitoring topNMonitor2VehicleCount = new TopNVehicleMonitoring(taskId,tuple._2,tuple._1);
            topNMonitor2VehicleCounts.add(topNMonitor2VehicleCount);
        }

        /**
         * 得到DAO 将数据插入数据库
         * 向数据库表 topn_monitor_car_count 中插入车流量最多的TopN数据
         */
        MonitoringDao TopNMonitor2CarCountDao = DaoFactory.getMonitorDAO();
        TopNMonitor2CarCountDao.insertBatchTopN(topNMonitor2VehicleCounts);

        /**
         * monitorId2MonitorIdRDD ---- K:monitor_id V:monitor_id
         * 获取topN卡口的详细信息
         * monitorId2MonitorIdRDD.join(monitorId2RowRDD)
         */
        List<Tuple2<String, String>> monitorId2CarCounts = new ArrayList<>();
        for(Tuple2<Integer,String> t : topNCarCount){
            monitorId2CarCounts.add(new Tuple2<String, String>(t._2, t._2));
        }
        JavaPairRDD<String, String> monitorId2MonitorIdRDD = sc.parallelizePairs(monitorId2CarCounts);
        return monitorId2MonitorIdRDD;
    }

    /**
     * 检测卡口状态
     * @param sc
     * @param spark
     * @param monitorId2CameraCountRDD
     * @param taskId
     * @param taskParamsJsonObject
     * @param monitorAndCameraStateAccumulator
     * @return RDD(实际卡扣对应车流量总数,对应的卡扣号)
     */
    private static JavaPairRDD<Integer,String> checkMonitorState(
            JavaSparkContext sc,
            SparkSession spark,
            JavaPairRDD<String, String> monitorId2CameraCountRDD,
            final long taskId,JSONObject taskParamsJsonObject,
            SelfDefineAccumulator monitorAndCameraStateAccumulator
            /*final Accumulator<String> monitorAndCameraStateAccumulator*/) {
        /**
         * 从monitor_camera_info标准表中查询出来每一个卡口对应的camera的数量
         */
        String sqlText = "SELECT * FROM monitor_camera_info";
        Dataset<Row> standardDF = spark.sql(sqlText);
        JavaRDD<Row> standardRDD = standardDF.javaRDD();
        /**
         * 使用mapToPair算子将standardRDD变成KV格式的RDD
         * monitorId2CameraId   :
         * (K:monitor_id  v:camera_id)
         */
        JavaPairRDD<String, String> monitorId2CameraId = standardRDD.mapToPair(
                new PairFunction<Row, String, String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple2<String, String> call(Row row)  {
                        return new Tuple2<String, String>(row.getString(0), row.getString(1));
                    }
                });

        /**
         * 对每一个卡扣下面的信息进行统计，统计出来camera_count（这个卡扣下一共有多少个摄像头）,camera_ids(这个卡扣下，所有的摄像头编号拼接成的字符串)
         * 返回：
         * 	("monitorId","cameraIds=xxx|cameraCount=xxx")
         * 例如：
         * 	("0008","cameraIds=02322,01213,03442|cameraCount=3")
         * 如何来统计？
         * 	1、按照monitor_id分组
         * 	2、使用mapToPair遍历，遍历的过程可以统计
         */
        JavaPairRDD<String, String> standardMonitor2CameraInfos = monitorId2CameraId.groupByKey()
                .mapToPair(new PairFunction<Tuple2<String,Iterable<String>>, String, String>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<String, String> call(Tuple2<String, Iterable<String>> tuple)  {
                        String monitorId = tuple._1;
                        Iterator<String> cameraIterator = tuple._2.iterator();
                        int count = 0;
                        StringBuilder cameraIds = new StringBuilder();
                        while(cameraIterator.hasNext()){
                            cameraIds.append(","+cameraIterator.next());
                            count++;
                        }
                        //cameraIds=00001,00002,00003,00004|cameraCount=4
                        String cameraInfos = Constants.FIELD_CAMERA_IDS+"="+cameraIds.toString().substring(1)+"|"
                                +Constants.FIELD_CAMERA_COUNT+"="+count;
                        return new Tuple2<String, String>(monitorId,cameraInfos);
                    }
                });

        /**
         * 将两个RDD进行比较，join  leftOuterJoin
         * 为什么使用左外连接？ 左：标准表里面的信息  右：实际信息
         */
        JavaPairRDD<String, Tuple2<String, Optional<String>>> joinResultRDD = standardMonitor2CameraInfos.leftOuterJoin(monitorId2CameraCountRDD);


        /**
         * carCount2MonitorId 最终返回的K,V格式的数据
         * K：实际监测数据中某个卡扣对应的总车流量
         * V：实际监测数据中这个卡扣 monitorId
         */
        JavaPairRDD<Integer, String> carCount2MonitorId = joinResultRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Tuple2<String,Tuple2<String,Optional<String>>>>, Integer, String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Iterator<Tuple2<Integer, String>> call(
                            Iterator<Tuple2<String, Tuple2<String, Optional<String>>>> iterator)  {

                        List<Tuple2<Integer, String>> list = new ArrayList<>();
                        while (iterator.hasNext()) {
                            //储藏返回值
                            Tuple2<String, Tuple2<String, Optional<String>>> tuple = iterator.next();
                            String monitorId = tuple._1;
                            String standardCameraInfos = tuple._2._1;
                            Optional<String> factCameraInfosOptional = tuple._2._2;
                            String factCameraInfos = "";

                            if(factCameraInfosOptional.isPresent()){
                                //这里面是实际检测数据中有标准卡扣信息
                                factCameraInfos = factCameraInfosOptional.get();
                            }else{
//                                //这里面是实际检测数据中没有标准卡扣信息
//						String standardCameraIds = StringUtil.getFieldFromConcatString(standardCameraInfos, "\\|", Constants.FIELD_CAMERA_IDS);
//						String[] split = standardCameraIds.split(",");
//						int abnoramlCameraCount = split.length;
//						StringBuilder abnormalCameraInfos = new StringBuilder();
//						for(String cameraId: split){
//							abnormalCameraInfos.append(","+cameraId);
//						}
//						//abnormalMonitorCount=1|abnormalCameraCount=3|abnormalMonitorCameraInfos="0002":07553,07554,07556
//						monitorAndCameraStateAccumulator.add(
//										 Constants.FIELD_ABNORMAL_MONITOR_COUNT +"=1|"
//										+Constants.FIELD_ABNORMAL_CAMERA_COUNT+"="+abnoramlCameraCount+"|"
//										+Constants.FIELD_ABNORMAL_MONITOR_CAMERA_INFOS+"="+monitorId+":"+abnormalCameraInfos.toString().substring(1));

                                String standardCameraIds =
                                        StringUtil.getFieldFromConcatString(standardCameraInfos, "\\|", Constants.FIELD_CAMERA_IDS);
                                String abnoramlCameraCount =
                                        StringUtil.getFieldFromConcatString(standardCameraInfos, "\\|", Constants.FIELD_CAMERA_COUNT);

                                //abnormalMonitorCount=1|abnormalCameraCount=3|abnormalMonitorCameraInfos="0002":07553,07554,07556
                                monitorAndCameraStateAccumulator.add(
                                        Constants.FIELD_ABNORMAL_MONITOR_COUNT +"=1|"
                                                +Constants.FIELD_ABNORMAL_CAMERA_COUNT+"="+abnoramlCameraCount+"|"
                                                +Constants.FIELD_ABNORMAL_MONITOR_CAMERA_INFOS+"="+monitorId+":"+standardCameraIds);
                                //跳出了本次while
                                continue;
                            }
                            /**
                             * 从实际数据拼接的字符串中获取摄像头数
                             */
                            int factCameraCount = Integer.parseInt(StringUtil.getFieldFromConcatString(factCameraInfos, "\\|", Constants.FIELD_CAMERA_COUNT));
                            /**
                             * 从标准数据拼接的字符串中获取摄像头数
                             */
                            int standardCameraCount = Integer.parseInt(StringUtil.getFieldFromConcatString(standardCameraInfos, "\\|", Constants.FIELD_CAMERA_COUNT));
                            if(factCameraCount == standardCameraCount){
                                /*
                                 * 	1、正常卡口数量
                                 * 	2、异常卡口数量
                                 * 	3、正常通道（此通道的摄像头运行正常）数，通道就是摄像头
                                 * 	4、异常卡口数量中哪些摄像头异常，需要保存摄像头的编号
                                 */
                                monitorAndCameraStateAccumulator.add(Constants.FIELD_NORMAL_MONITOR_COUNT+"=1|"+Constants.FIELD_NORMAL_CAMERA_COUNT+"="+factCameraCount);
                            }else{
                                /**
                                 * 从实际数据拼接的字符串中获取摄像编号集合
                                 */
                                String factCameraIds = StringUtil.getFieldFromConcatString(factCameraInfos, "\\|", Constants.FIELD_CAMERA_IDS);

                                /**
                                 * 从标准数据拼接的字符串中获取摄像头编号集合
                                 */
                                String standardCameraIds = StringUtil.getFieldFromConcatString(standardCameraInfos, "\\|", Constants.FIELD_CAMERA_IDS);

                                List<String> factCameraIdList = Arrays.asList(factCameraIds.split(","));
                                List<String> standardCameraIdList = Arrays.asList(standardCameraIds.split(","));
                                StringBuilder abnormalCameraInfos = new StringBuilder();
                                int abnormalCameraCount = 0;//不正常摄像头数
                                int normalCameraCount = 0;//正常摄像头数
                                for (String cameraId : standardCameraIdList) {
                                    if(!factCameraIdList.contains(cameraId)){
                                        abnormalCameraCount++;
                                        abnormalCameraInfos.append(","+cameraId);
                                    }
                                }
                                normalCameraCount = standardCameraIdList.size()-abnormalCameraCount;
                                //往累加器中更新状态
                                monitorAndCameraStateAccumulator.add(
                                        Constants.FIELD_ABNORMAL_MONITOR_COUNT+"=1|"
                                                +Constants.FIELD_NORMAL_CAMERA_COUNT+"="+normalCameraCount+"|"
                                                +Constants.FIELD_ABNORMAL_CAMERA_COUNT+"="+abnormalCameraCount+"|"
                                                +Constants.FIELD_ABNORMAL_MONITOR_CAMERA_INFOS+"="+monitorId + ":" + abnormalCameraInfos.toString().substring(1));
                            }
                            //从实际数据拼接到字符串中获取车流量
                            int carCount = Integer.parseInt(StringUtil.getFieldFromConcatString(factCameraInfos, "\\|", Constants.FIELD_CAR_COUNT));
                            list.add(new Tuple2<Integer, String>(carCount,monitorId));
                        }
                        //最后返回的list是实际监测到的数据中，list[(卡扣对应车流量总数,对应的卡扣号),... ...]
                        return  list.iterator();
                    }
                });
        return carCount2MonitorId;
    }
}
