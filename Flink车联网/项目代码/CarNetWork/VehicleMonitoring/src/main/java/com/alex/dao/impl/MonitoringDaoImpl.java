package com.alex.dao.impl;

import com.alex.bean.MonitoringState;
import com.alex.bean.TopNVehicleMonitoring;
import com.alex.dao.MonitoringDao;
import com.alex.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:32
 * @Description 卡扣流量监控Dao实现类
 */
public class MonitoringDaoImpl implements MonitoringDao {
    /**
     * 向数据库表 topN_monitoring_car_count中插入车流量最多的TopN数据
     * @param topNVehicleMonitorings
     */
    @Override
    public void insertBatchTopN(List<TopNVehicleMonitoring> topNVehicleMonitorings) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO topn_monitor_car_count VALUES(?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for (TopNVehicleMonitoring sum: topNVehicleMonitorings) {
            params.add(new Object[]{sum.getTaskId(),sum.getMonitorId(),sum.getCarCount()});
        }
        jdbcUtil.executeBatch(sql,params);
    }

    /**
     * 将topN的卡扣车流量明细数据 存入topn_monitor_detail_info 表中
     * @param topNVehicleMonitorings
     */
    @Override
    public void insertBatchMonitoringDetail(List<TopNVehicleMonitoring> topNVehicleMonitorings) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO topn_monitor_detail_info VALUES(?,?,?,?,?,?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for (TopNVehicleMonitoring detail: topNVehicleMonitorings) {
            params.add(new Object[]{detail.getTaskId(),detail.getDate(),detail.getMonitorId(),detail.getCameraId(),detail.getCar(),detail.getActionTime(),detail.getSpeed(),detail.getRoadId()});
        }
        jdbcUtil.executeBatch(sql,params);
    }

    /**
     * 向数据库表monitor_state中添加累加器累计的各个值
     * @param monitoringState
     */
    @Override
    public void insertMonitoringState(MonitoringState monitoringState) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO monitor_state VALUES(?,?,?,?,?,?)";
        Object[] param = new Object[]{
                monitoringState.getTaskId(),
                monitoringState.getNormalMonitorCount(),
                monitoringState.getNormalCameraCount(),
                monitoringState.getAbnormalMonitorCount(),
                monitoringState.getAbnormalCameraCount(),
                monitoringState.getAbnormalMonitorCameraInfos()};
        List<Object[]> params = new ArrayList<>();
        params.add(param);
        jdbcUtil.executeBatch(sql,params);
    }

    /**
     * 向数据库表top10_speed_detail中插入数据
     * @param topNVehicleMonitorings
     */
    @Override
    public void insertBatchTopNDetail(List<TopNVehicleMonitoring> topNVehicleMonitorings) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO top10_speed_detail VALUES(?,?,?,?,?,?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for (TopNVehicleMonitoring m: topNVehicleMonitorings) {
            params.add(new Object[]{m.getTaskId(),m.getDate(),m.getMonitorId(),m.getCameraId(),m.getCar(),m.getActionTime(),m.getSpeed(),m.getRoadId()});
        }
        jdbcUtil.executeBatch(sql,params);
    }
}
