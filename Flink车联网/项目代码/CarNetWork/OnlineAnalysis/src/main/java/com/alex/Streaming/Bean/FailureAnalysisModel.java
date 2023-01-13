package com.alex.Streaming.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-13 11:56
 * @Description  定义远程故障诊断的车辆基础信息表的javaBean对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureAnalysisModel {
    //车架号
    private String vin;
    //车型编码
    private String modelCode;
    //车型名称
    private String modelName;
    //车系编码
    private String seriesCode;
    //车系名称
    private String seriesName;
    //出售日期
    private String salesDate;
    //车型
    private String carType;
    //车辆类型简称
    private String nickName;
    //年限
    private String liveTime;
}
