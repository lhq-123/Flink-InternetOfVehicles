package com.alex.Streaming.Bean.ElectricFence;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Alex_liu
 * @create 2023-01-12 19:20
 * @Description 定义查询mysql数据库的电子围栏车辆表与电子围栏规则表的数据对应的javabean对象
 */
@Data
@AllArgsConstructor
public class ElectricFenceResultTmp {
    //电子围栏id
    private int id;
    //电子围栏名称
    private String name;
    //电子围栏中心地址
    private String address;
    //电子围栏半径
    private float radius;
    //电子围栏中心点的经度
    private double longitude;
    //电子围栏中心点的维度
    private double latitude;
    //电子围栏的开始时间
    private Date startTime;
    //电子围栏的结束时间
    private Date endTime;
    // 省略get、set、有参构造方法
}
