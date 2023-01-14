package com.alex.Streaming.Sink.ElectricFence;

import com.alex.Streaming.Bean.ElectricFence.ElectricFenceModel;
import com.alex.Streaming.Utils.DateUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alex_Alex_liu
 * @create 2023-01-12 21:30
 * @Description  自定义mysql的输出sink
 */
public class ElectricFenceToMysql extends RichSinkFunction<ElectricFenceModel> {
    Logger logger = LoggerFactory.getLogger(ElectricFenceToMysql.class);
    Connection conn  = null;
    PreparedStatement preSta = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        ParameterTool globalJobParameters = (ParameterTool)getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String url = globalJobParameters.getRequired("jdbc.url");
        String user = globalJobParameters.getRequired("jdbc.user");
        String pwd = globalJobParameters.getRequired("jdbc.password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pwd);
    }

    @Override
    public void invoke(ElectricFenceModel value, Context context) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //出围栏(且能获取到进围栏状态的)则修改进围栏的状态
            if (value.getStatusAlarm() == 0 && value.getInMysql()) {
                String executeSql = "update vehicle_networking.electric_fence set outTime=?,gpsTime=?,lat=?,lng=?,terminalTime=?,processTime=? where id=?";
                preSta = conn.prepareStatement(executeSql);
                preSta.setObject(1, value.getOutEleTime());
                preSta.setObject(2, value.getGpsTime());
                preSta.setDouble(3, value.getLat());
                preSta.setDouble(4, value.getLng());
                preSta.setObject(5, value.getTerminalTime());
                preSta.setObject(6, sdf.format(new Date()));
                preSta.setLong(7, value.getUuid());
            } else {
                // 进入围栏，转换ElectricFenceModel对象，插入结构数据到电子围栏结果表
                String executeSql = "insert into vehicle_networking.electric_fence(vin,inTime,outTime,gpsTime,lat,lng,eleId,eleName,address,latitude,longitude,radius,terminalTime,processTime) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preSta = conn.prepareStatement(executeSql);
                preSta.setString(1, value.getVin());
                preSta.setObject(2, value.getInEleTime());
                preSta.setObject(3, value.getOutEleTime());
                preSta.setObject(4, value.getGpsTime());
                preSta.setDouble(5, value.getLat());
                preSta.setDouble(6, value.getLng());
                preSta.setInt(7, value.getEleId());
                preSta.setString(8, value.getEleName());
                preSta.setString(9, value.getAddress());
                preSta.setDouble(10, value.getLatitude());
                preSta.setDouble(11, value.getLongitude());
                preSta.setFloat(12, value.getRadius());
                preSta.setObject(13, value.getTerminalTime());
                preSta.setObject(14, DateUtil.getCurrentDate());
            }
            preSta.execute();
            logger.info("MysqlSink，批量插入数据成功，插入{}条数据", preSta.getMaxRows());
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preSta != null) preSta.close();
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (preSta != null) {
                preSta.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
