package com.alex.Streaming.Sink.FailureAnalysisSink;

import com.alex.Streaming.Bean.FailureAnalysisDataObj;
import com.alex.Streaming.Utils.ConfigLoader;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Alex_liu
 * @create 2023-01-13 15:08
 * @Description 实现远程故障在线分析统计数据的实时写入mysql数据库
 */
public class FailureAnalysisToMysql  extends RichSinkFunction<FailureAnalysisDataObj> {
    private static Logger logger = LoggerFactory.getLogger(FailureAnalysisToMysql.class);
    //定义connection连接对象
    Connection connection = null;
    //定义statement
    PreparedStatement preSta = null;

    /**
     * 初始化操作。初始化mysql的连接对象
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //实例化connection对象
        String url = ConfigLoader.get("jdbc.url");
        String user = ConfigLoader.get("jdbc.user");
        String passWord = ConfigLoader.get("jdbc.password");
        connection = DriverManager.getConnection(url, user, passWord);
        connection.setAutoCommit(false);
        //实例化statement
        String sql = "INSERT INTO online_data(vin,process_time,lat,lng, mileage,is_alarm,alarm_name,terminal_time,earliest_time,max_voltage_battery,min_voltage_battery,max_temperature_value,min_temperature_value,speed,soc,charge_flag,total_voltage,total_current,battery_voltage,probe_temperatures,series_name,model_name,live_time,sales_date,car_type,province,city, county) values(?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?) \n" +
                "ON DUPLICATE KEY UPDATE process_time=now(),lat=?,lng=?,mileage=?,is_alarm=?, alarm_name=?,terminal_time=?,max_voltage_battery=?,\n" +
                "min_voltage_battery=?,max_temperature_value=?, min_temperature_value=?, speed=?,soc=?,charge_flag=?, total_voltage=?,\n" +
                "total_current=?,battery_voltage=?, probe_temperatures=?,series_name=?,model_name=?,live_time=?,sales_date=?,car_type=?,\n" +
                "province=?,city=?,county=?";
        preSta = connection.prepareStatement(sql);
    }

    /**
     * 关闭释放资源
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        try {
            if(preSta != null) preSta.close();
            if(connection!=null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据一条条的写入到mysql数据库中
     * @param FailureAnalysisDataObj
     * @param context
     * @throws Exception
     */
    @Override
    public void invoke(FailureAnalysisDataObj FailureAnalysisDataObj, Context context) throws Exception {
        try {
            //插入数据的参数
            preSta.setString(1, FailureAnalysisDataObj.getVin());
            preSta.setDouble(2, FailureAnalysisDataObj.getLat());
            preSta.setDouble(3, FailureAnalysisDataObj.getLng());
            preSta.setDouble(4, FailureAnalysisDataObj.getMileage());
            preSta.setInt(5, FailureAnalysisDataObj.getIsAlarm());
            preSta.setString(6, FailureAnalysisDataObj.getAlarmName());
            preSta.setString(7, FailureAnalysisDataObj.getTerminalTime());
            preSta.setString(8, FailureAnalysisDataObj.getEarliestTime());
            preSta.setDouble(9, FailureAnalysisDataObj.getMaxVoltageBattery());
            preSta.setDouble(10, FailureAnalysisDataObj.getMinVoltageBattery());
            preSta.setDouble(11, FailureAnalysisDataObj.getMaxTemperatureValue());
            preSta.setDouble(12, FailureAnalysisDataObj.getMinTemperatureValue());
            preSta.setDouble(13, FailureAnalysisDataObj.getSpeed());
            preSta.setInt(14, FailureAnalysisDataObj.getSoc());
            preSta.setInt(15, FailureAnalysisDataObj.getChargeFlag());
            preSta.setDouble(16, FailureAnalysisDataObj.getTotalVoltage());
            preSta.setDouble(17, FailureAnalysisDataObj.getTotalCurrent());
            preSta.setString(18, FailureAnalysisDataObj.getBatteryVoltage());
            preSta.setString(19, FailureAnalysisDataObj.getProbeTemperatures());
            preSta.setString(20, FailureAnalysisDataObj.getSeriesName());
            preSta.setString(21, FailureAnalysisDataObj.getModelName());
            preSta.setString(22, FailureAnalysisDataObj.getLiveTime());
            preSta.setString(23, FailureAnalysisDataObj.getSalesDate());
            preSta.setString(24, FailureAnalysisDataObj.getCarType());
            preSta.setString(25, FailureAnalysisDataObj.getProvince());
            preSta.setString(26, FailureAnalysisDataObj.getCity());
            preSta.setString(27, FailureAnalysisDataObj.getCounty());
            //修改数据的参数
            preSta.setDouble(28, FailureAnalysisDataObj.getLat());
            preSta.setDouble(29, FailureAnalysisDataObj.getLng());
            preSta.setDouble(30, FailureAnalysisDataObj.getMileage());
            preSta.setInt(31, FailureAnalysisDataObj.getIsAlarm());
            preSta.setString(32, FailureAnalysisDataObj.getAlarmName());
            preSta.setString(33, FailureAnalysisDataObj.getTerminalTime());
            preSta.setDouble(34, FailureAnalysisDataObj.getMaxVoltageBattery());
            preSta.setDouble(35, FailureAnalysisDataObj.getMinVoltageBattery());
            preSta.setDouble(36, FailureAnalysisDataObj.getMaxTemperatureValue());
            preSta.setDouble(37, FailureAnalysisDataObj.getMinTemperatureValue());
            preSta.setDouble(38, FailureAnalysisDataObj.getSpeed());
            preSta.setInt(39, FailureAnalysisDataObj.getSoc());
            preSta.setInt(40, FailureAnalysisDataObj.getChargeFlag());
            preSta.setDouble(41, FailureAnalysisDataObj.getTotalVoltage());
            preSta.setDouble(42, FailureAnalysisDataObj.getTotalCurrent());
            preSta.setString(43, FailureAnalysisDataObj.getBatteryVoltage());
            preSta.setString(44, FailureAnalysisDataObj.getProbeTemperatures());
            preSta.setString(45, FailureAnalysisDataObj.getSeriesName());
            preSta.setString(46, FailureAnalysisDataObj.getModelName());
            preSta.setString(47, FailureAnalysisDataObj.getLiveTime());
            preSta.setString(48, FailureAnalysisDataObj.getSalesDate());
            preSta.setString(49, FailureAnalysisDataObj.getCarType());
            preSta.setString(50, FailureAnalysisDataObj.getProvince());
            preSta.setString(51, FailureAnalysisDataObj.getCity());
            preSta.setString(52, FailureAnalysisDataObj.getCounty());

            //执行数据更新和递交操作
            preSta.execute();
            connection.commit();
        } catch (SQLException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        }
    }
}
