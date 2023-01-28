package com.alex.mock;

import com.alex.utils.DateUtil;
import com.alex.utils.StringUtil;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Alex_liu
 * @create 2023-01-28 16:55
 * @Description
 */
public class MockData {
    private final static Logger logger = LoggerFactory.getLogger(MockData.class);
    public static void mock(JavaSparkContext sc, SparkSession spark) {
        List<Row> dataList = new ArrayList<Row>();
        Random random = new Random();

        String[] locations = new String[]{"鲁","京","京","京","沪","京","京","深","京","京"};
        //String[] areas = new String[]{"海淀区","朝阳区","昌平区","东城区","西城区","丰台区","顺义区","大兴区"};
        //date :如：2018-01-01
        String date = DateUtil.getTodayDate();

        /**
         * 模拟3000个车辆
         */
        for (int i = 0; i < 3000; i++) {
            //模拟车牌号：如：京A00001
            String car = locations[random.nextInt(10)] + (char)(65+random.nextInt(26))+StringUtil.fulfuill(5,random.nextInt(100000)+"");

            //baseActionTime 模拟24小时
            String baseActionTime = date + " " + StringUtil.fulfuill(random.nextInt(24)+"");//2018-01-01 01
            /**
             * 这里的for循环模拟每辆车经过不同的卡扣不同的摄像头 数据。
             */
            for(int j = 0 ; j < (random.nextInt(300)+1) ; j++){
                //模拟每个车辆每被30个摄像头拍摄后 时间上累计加1小时。这样做使数据更加真实。
                if(j % 30 == 0 && j != 0){
                    baseActionTime = date + " " + StringUtil.fulfuill((Integer.parseInt(baseActionTime.split(" ")[1])+1)+"");
                }

                String areaId = StringUtil.fulfuill(2,random.nextInt(8)+1+"");//模拟areaId 【一共8个区域】

                String roadId = random.nextInt(50)+1+"";//模拟道路id 【1~50 个道路】

                String monitorId = StringUtil.fulfuill(4, random.nextInt(9)+"");//模拟9个卡扣monitorId，0补全4位

                String cameraId = StringUtil.fulfuill(5, random.nextInt(100000)+"");//模拟摄像头id cameraId

                String actionTime = baseActionTime + ":"
                        + StringUtil.fulfuill(random.nextInt(60)+"") + ":"
                        + StringUtil.fulfuill(random.nextInt(60)+"");//模拟经过此卡扣开始时间 ，如：2018-01-01 20:09:10

                String speed = (random.nextInt(260)+1)+"";//模拟速度

                Row row = RowFactory.create(date,monitorId,cameraId,car,actionTime,speed,roadId,areaId);
                dataList.add(row);
            }
        }

        /**
         * 2018-4-20 1	22	京A1234
         * 2018-4-20 1	23	京A1234
         * 1 【22,23】
         * 1 【22,23,24】
         */

        JavaRDD<Row> rowRdd = sc.parallelize(dataList);

        StructType cameraFlowSchema = DataTypes.createStructType(Arrays.asList(
                DataTypes.createStructField("date", DataTypes.StringType, true),
                DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
                DataTypes.createStructField("camera_id", DataTypes.StringType, true),
                DataTypes.createStructField("car", DataTypes.StringType, true),
                DataTypes.createStructField("action_time", DataTypes.StringType, true),
                DataTypes.createStructField("speed", DataTypes.StringType, true),
                DataTypes.createStructField("road_id", DataTypes.StringType, true),
                DataTypes.createStructField("area_id", DataTypes.StringType, true)
        ));

        Dataset<Row> ds = spark.createDataFrame(rowRdd, cameraFlowSchema);

        //默认打印出来df里面的20行数据
        logger.info("-------车辆信息数据----");
        ds.show();
        ds.registerTempTable("monitor_flow_action");

        /**
         * monitorAndCameras    key：monitor_id
         * 						value:hashSet(camera_id)
         * 基于生成的数据，生成对应的卡扣号和摄像头对应基本表
         */
        Map<String,Set<String>> monitorAndCameras = new HashMap<>();

        int index = 0;
        for(Row row : dataList){
            //row.getString(1) monitor_id
            Set<String> sets = monitorAndCameras.get(row.getString(1));
            if(sets == null){
                sets = new HashSet<>();
                monitorAndCameras.put((String)row.getString(1), sets);
            }
            //这里每隔1000条数据随机插入一条数据，模拟出来标准表中卡扣对应摄像头的数据比模拟数据中多出来的摄像头。这个摄像头的数据不一定会在车辆数据中有。即可以看出卡扣号下有坏的摄像头。
            index++;
            if(index % 1000 == 0){
                sets.add(StringUtil.fulfuill(5, random.nextInt(100000)+""));
            }
            //row.getString(2) camera_id
            String cameraId = row.getString(2);
            sets.add(cameraId);
        }

        dataList.clear();

        Set<Map.Entry<String,Set<String>>> entrySet = monitorAndCameras.entrySet();
        for (Map.Entry<String, Set<String>> entry : entrySet) {
            String monitor_id = entry.getKey();
            Set<String> sets = entry.getValue();
            Row row = null;
            for (String camera_id : sets) {
                row = RowFactory.create(monitor_id,camera_id);
                dataList.add(row);
            }
        }

        StructType monitorSchema = DataTypes.createStructType(Arrays.asList(
                DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
                DataTypes.createStructField("camera_id", DataTypes.StringType, true)
        ));


        rowRdd = sc.parallelize(dataList);
        Dataset<Row> monitorDF = spark.createDataFrame(rowRdd, monitorSchema);
        monitorDF.registerTempTable("monitor_camera_info");
        logger.info("----卡扣号对应摄像头号 数据----");
        monitorDF.show();
    }
}
