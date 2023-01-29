package com.alex.dao;

import com.alex.bean.MonitoringState;
import com.alex.bean.TopNVehicleMonitoring;

import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:31
 * @Description 车辆卡扣监控管理
 *    1.卡扣流量TopN批量插入数据库
 *    2.卡扣车辆具体信息批量插入数据库
 *    3.卡扣状态信息批量插入数据库
 */
public interface MonitoringDao {
    void insertBatchTopN(List<TopNVehicleMonitoring> topNVehicleMonitorings);
    void insertBatchMonitoringDetail(List<TopNVehicleMonitoring> topNVehicleMonitorings);
    void insertMonitoringState(MonitoringState monitoringState);
    void insertBatchTopNDetail(List<TopNVehicleMonitoring> topNVehicleMonitorings);
}
