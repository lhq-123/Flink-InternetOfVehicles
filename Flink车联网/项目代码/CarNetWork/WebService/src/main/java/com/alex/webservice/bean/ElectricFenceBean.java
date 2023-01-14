package com.alex.webservice.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-14 1:45
 * @Description:  电子围栏数据服务接口对象
*/
@Getter
@Setter
public class ElectricFenceBean {
    //车架号 
    private String vin;
    //进电子围栏时间
    private String inTime;
    //出电子围栏时间
    private String outTime;
    //位置时间
    private String gpsTime;
    //位置纬度
    private Double lat;
    //位置经度
    private Double lng;
    //电子围栏ID
    private int eleId;
    //电子围栏名称
    private String eleName;
    //中心点地址
    private String address;
    //中心点纬度
    private Double latitude;
    //中心点经度
    private Double longitude;
    //电子围栏半径
    private Float radius;
    //终端时间
    private String terminalTime;
    //插入数据的时间
    private String processTime;
}