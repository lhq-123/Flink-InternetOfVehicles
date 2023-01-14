package com.alex.webservice.service;

import com.alex.webservice.bean.ElectricFenceBean;

import java.util.List;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 2:04
 * @Description TODO 电子围栏后台数据服务接口,服务接口类
 */
public interface ElectricFenceService {
    List<ElectricFenceBean> queryAll(Integer pageNo, Integer pageSize);
    Long totalNum();
}