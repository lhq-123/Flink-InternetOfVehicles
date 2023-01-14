package com.alex.webservice.dao;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/18 15:15
 * @Description TODO
 */
public interface VehicleViewDao {
    // todo 统计平台车辆总数 根据vin去重
    Long totalNum();
    // todo 统计平台在线车辆 根据当天的00:00:00时间统计车辆总数
    Long onlineNum();
    // todo 统计平台行驶中的车辆 根据当天的当前时间统计30秒内的数据
    Integer drivingNum();
}
