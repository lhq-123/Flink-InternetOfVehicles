package com.alex.webservice.service;

import com.alex.webservice.bean.ElectricFenceBean;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-15 2:04
 * @Description 电子围栏后台数据服务接口,服务接口类
 */
public interface ElectricFenceService {
    List<ElectricFenceBean> queryAll(Integer pageNo, Integer pageSize);
    Long totalNum();
}