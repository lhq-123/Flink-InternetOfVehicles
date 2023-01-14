package com.alex.Streaming.Bean.FailureAnalysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alex_liu
 * @create 2023-01-13 11:16
 * @Description  定义远程诊断实时故障分析结果javaBean对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class FailureAnalysisDataObj implements Serializable {
        //车架号
        private String vin;
        //数据更新时间
        private String processTime;
        //纬度
        private double lat;
        //经度
        private double lng;
        //里程表读数
        private double mileage;
        //故障标志(0正常，1故障)
        private int isAlarm;
        //故障名称(多个故障用~分割)
        private String alarmName;
        //终端时间
        private String terminalTime;
        //最早数据接收时间
        private String earliestTime;
        //单体电池最高电压
        private double maxVoltageBattery;
        //单体电池最低电压
        private double minVoltageBattery;
        //电池最高温度
        private double maxTemperatureValue;
        //电池最低温度
        private double minTemperatureValue;
        //车速
        private double speed;
        //SOC
        private int soc;
        //充电标识 0：未充电 1：充电 2：异常
        private int chargeFlag;
        //总电压,单位：V，实际取值0.1~100V
        private double totalVoltage;
        //总电流,单位：A,实际取值为-1000~1000A
        private double totalCurrent;
        //单体电池电压列表
        private String batteryVoltage;
        //电池模块温度列表
        private String probeTemperatures;
        //车系
        private String seriesName;
        //车型
        private String modelName;
        //年限(单位:月，未查到数据显示-1)
        private String liveTime;
        //销售日期
        private String salesDate;
        //车辆类型
        private String carType;
        //省份
        private String province;
        //城市
        private String city;
        //区（县）
        private String county;
}
