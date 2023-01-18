package com.alex.webservice.service;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-15 1:53
 * @Description 热力图后台数据服务接口,服务接口类
 */
public interface HeatMapService {
    List<List<Double>> queryPointsByCity(String city);
    List<List<Double>> queryAllPoints();
}