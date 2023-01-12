package com.alex.Streaming.Source;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 21:03
 * @Description  自定义 Mysql source
 */
public class MysqlVehicleInfoSource extends RichSourceFunction<HashMap<String,Long>> {

    private Logger logger = LoggerFactory.getLogger(MysqlVehicleInfoSource.class);
    ParameterTool globalJobParameters;
    Connection conn = null;
    PreparedStatement preSta = null;
    boolean isRunning = true;
    HashMap vehicleInfoMap = new HashMap<String, Long>();

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("com.mysql.cj.jdbc.Driver");
        globalJobParameters = (ParameterTool) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        //注册驱动

        //实例化connection对象
        String url = globalJobParameters.getRequired("jdbc.url");
        String user = globalJobParameters.getRequired("jdbc.user");
        String password = globalJobParameters.getRequired("jdbc.password");
        conn = DriverManager.getConnection(url,user,password);
        String querySql = "select vin,min(id) id from vehicle_networking.electric_fence where inTime is not null and outTime is null group by vin";
        preSta = conn.prepareStatement(querySql);

    }

    @Override
    public void run(SourceContext<HashMap<String, Long>> sourceContext) throws Exception {
        while (isRunning) {
            try {
                vehicleInfoMap.clear();
                ResultSet resultSet = preSta.executeQuery();
                while (resultSet.next()) {
                    vehicleInfoMap.put(resultSet.getString("vin"), resultSet.getLong("id"));
                }
                if (vehicleInfoMap.isEmpty()) {
                    logger.warn("从mysql中electronic_fence相关表取的数据为空");
                } else {
                    sourceContext.collect(vehicleInfoMap);
                    logger.info("查询电子围栏分析结果表中数据，存在记录数为:{}条.", vehicleInfoMap.size());
                }
                //每1秒钟获取一次最新数据，因为窗口每隔90s进行一次计算，因此该时间一定要小于窗口触发计算的频率
                TimeUnit.SECONDS.sleep(1);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public void close() throws Exception {
        try {
            if (preSta != null && !preSta.isClosed()) {
                preSta.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
