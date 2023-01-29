package com.alex.dao;

import com.alex.bean.RandomExtract;
import com.alex.bean.RandomExtractMonitoring;

import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:31
 * @Description 随机抽取车辆信息
 */
public interface RandomExtractDao {
    void insertBatchRandomExtractVehicle(List<RandomExtract> randomExtracts);
    void insertBatchRandomExtractMonitoring(List<RandomExtractMonitoring> randomExtractMonitorings);
}
