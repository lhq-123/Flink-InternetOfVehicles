package com.alex.webservice.service.impl;

import com.alex.webservice.bean.HeatMapPointBean;
import com.alex.webservice.mapper.HeatMapMapper;
import com.alex.webservice.service.HeatMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-15 1:52
 * @Description: 热力图后台数据服务接口，service实现类
 */
@Service
public class HeatMapServiceImpl implements HeatMapService {

    @Autowired
    private HeatMapMapper heatMapMapper;

    @Override
    public List<List<Double>> queryPointsByCity(String city) {
        List<HeatMapPointBean> heatMapPointBeanList = heatMapMapper.queryPointsByCity(city);
        List<List<Double>> resultList = new ArrayList<>();
        heatMapPointBeanList.forEach(heatMapPointBean -> {
            List<Double> points = new ArrayList<>(2);
            points.add(heatMapPointBean.getLongitude());
            points.add(heatMapPointBean.getLatitude());
            resultList.add(points);

        });
        return resultList;
    }

    @Override
    public List<List<Double>> queryAllPoints() {
        List<HeatMapPointBean> heatMapPointBeanList = heatMapMapper.queryAllPoints();
        List<List<Double>> resultList = new ArrayList<>();
        heatMapPointBeanList.forEach(heatMapPointBean -> {
            List<Double> points = new ArrayList<>(2);
            points.add(heatMapPointBean.getLongitude());
            points.add(heatMapPointBean.getLatitude());
            resultList.add(points);

        });
        return resultList;
    }
}