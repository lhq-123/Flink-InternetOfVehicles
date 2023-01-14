package com.alex.webservice.service;

import com.alex.webservice.bean.VehicleDataRateBean;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-14 2:01
 * @Description
 */
public interface VehicleDataRateService {
    List<VehicleDataRateBean> queryAll(int pageNo, int pageSize);
    Long totalNum();
}
