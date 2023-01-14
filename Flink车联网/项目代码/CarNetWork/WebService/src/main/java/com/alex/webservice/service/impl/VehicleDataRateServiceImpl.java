package com.alex.webservice.service.impl;

import com.alex.webservice.bean.VehicleDataRateBean;
import com.alex.webservice.mapper.vehicleDataRateMapper;
import com.alex.webservice.service.VehicleDataRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 2:01
 * @Description TODO 数据正确率和错误率后台数据服务接口，service实现类
 */
@Service
public class VehicleDataRateServiceImpl implements VehicleDataRateService {
    @Autowired
    private vehicleDataRateMapper vehicleDataRateMapper;

    @Override
    public List<VehicleDataRateBean> queryAll(int pageNo, int pageSize) {
        if (pageNo <= 1) {
            pageNo = 0;
        } else {
            pageNo -= 1;
        }
        return vehicleDataRateMapper.queryAll(pageNo * pageSize, pageSize);
    }

    @Override
    public Long totalNum() {
        return vehicleDataRateMapper.totalNum();
    }
}