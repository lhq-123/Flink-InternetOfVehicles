package com.alex.Streaming.Source;

import com.alex.Streaming.Bean.ElectricFenceResultTmp;
import com.alex.Streaming.Utils.DateUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 19:20
 * @Description
 *          读取已经存在的电子围栏中的车辆和电子围栏规则表的数据
 *          String：vin车架号（电子围栏中的车辆）
 *          ElectricFenceResultTmp：电子围栏规则表的数据（电子围栏规则表）
 */
public class MysqlElectricFenceSource extends RichSourceFunction<HashMap<String, ElectricFenceResultTmp>> {
    private static Logger logger = LoggerFactory.getLogger(MysqlElectricFenceSource.class);
    ParameterTool globalJobParameters;
    //定义connection连接对象
    Connection connection = null;
    //定义statement
    Statement statement = null;
    //定义boolean, 是否运行的标记
    Boolean isRunning = true;

    /**
     * 初始化资源
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        globalJobParameters = (ParameterTool) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //实例化connection对象
        String url = globalJobParameters.getRequired("jdbc.url");
        String user = globalJobParameters.getRequired("jdbc.user");
        String password = globalJobParameters.getRequired("jdbc.password");
        //实例化statement
        statement = connection.createStatement();
    }

    /**
     *  核心方法
     * @param sourceContext
     * @throws Exception
     */
    @Override
    public void run(SourceContext<HashMap<String, ElectricFenceResultTmp>> sourceContext) throws Exception {
        try {
            while (isRunning){
                //定义返回的hashmap对象
                HashMap<String, ElectricFenceResultTmp> vehicleInfoMap = new HashMap<>();
                ResultSet resultSet = statement.executeQuery("" +
                        "select vin,name,address,radius,longitude,latitude,start_time,end_time,setting.id \" +\n" +
                        "\"from vehicle_networking.electronic_fence_vins vins \" +\n" +
                        "\"INNER JOIN vehicle_networking.electronic_fence_setting setting " +
                        "on vins.setting_id=setting.id and status=1");
                while (resultSet.next()){
                    vehicleInfoMap.put(resultSet.getString("vin"),new ElectricFenceResultTmp(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getFloat("radius"),
                            resultSet.getDouble("longitude"),
                            resultSet.getDouble("latitude"),
                            DateUtil.convertStringToDate(resultSet.getString("start_time")),
                            DateUtil.convertStringToDate(resultSet.getString("end_time"))
                    ));
                }
                if (vehicleInfoMap.isEmpty()){
                    logger.info("从mysql中的electronic_fence_setting、electronic_fence_vins表中读取相关数据为空.");
                }else {
                    logger.info("从mysql中的electronic_fence_setting、electronic_fence_vins表中查询到的车辆总数为:"+vehicleInfoMap.size());
                }
                resultSet.close();
                logger.info("vehicleInfoMap" + vehicleInfoMap);
                sourceContext.collect(vehicleInfoMap);
                //两次业务表查询的时间间隔
                logger.info("vehicleInfo.millionSeconds:"+globalJobParameters.getRequired("vehicleInfo.millionSeconds"));
                TimeUnit.SECONDS.sleep(Integer.parseInt(globalJobParameters.getRequired("vehicleInfo.millionSeconds")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error(throwables.getMessage());
        }
    }

    /**
     * 取消操作
     */
    @Override
    public void cancel() {
        isRunning = false;
    }

    /**
     * 关闭连接，释放资源
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
}
