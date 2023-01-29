package com.alex.dao.impl;

import com.alex.bean.RandomExtract;
import com.alex.bean.RandomExtractMonitoring;
import com.alex.dao.RandomExtractDao;
import com.alex.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:33
 * @Description 随机抽取车辆信息管理Dao实现类
 */
public class RandomExtractDaoImpl implements RandomExtractDao {
    @Override
    public void insertBatchRandomExtractVehicle(List<RandomExtract> vehicleRandomExtracts) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO random_extract_car VALUES(?,?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for (RandomExtract vehicleExtract: vehicleRandomExtracts) {
            params.add(new Object[]{vehicleExtract.getTaskId(),vehicleExtract.getCar(),vehicleExtract.getDate(),vehicleExtract.getDateHour()});
        }
        jdbcUtil.executeBatch(sql,params);
    }

    @Override
    public void insertBatchRandomExtractMonitoring(List<RandomExtractMonitoring> randomExtractMonitorings) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO random_extract_car_detail_info VALUES(?,?,?,?,?,?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for (RandomExtractMonitoring r: randomExtractMonitorings) {
            params.add(new Object[]{r.getTaskId(),r.getDate(),r.getMonitorId(),r.getCameraId(),r.getCar(),r.getActionTime(),r.getSpeed(),r.getRoadId()});
        }
        jdbcUtil.executeBatch(sql,params);
    }
}
