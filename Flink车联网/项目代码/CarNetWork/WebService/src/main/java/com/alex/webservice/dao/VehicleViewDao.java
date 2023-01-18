package com.alex.webservice.dao;

/**
 * @author Alex_liu
 * @Date 2023-01-15 15:15
 * @Description
 */
public interface VehicleViewDao {
    // 统计平台车辆总数 根据vin去重
    Long totalNum();
    // 统计平台在线车辆 根据当天的00:00:00时间统计车辆总数
    Long onlineNum();
    // 统计平台行驶中的车辆 根据当天的当前时间统计30秒内的数据
    Integer drivingNum();
}
