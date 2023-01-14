package com.alex.webservice.mapper;

import com.alex.webservice.bean.HeatMapPointBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 1:51
 * @Description TODO 热力图后台数据服务接口Mapper接口，对应xml文件名称
 */
@Repository
public interface HeatMapMapper {
    List<HeatMapPointBean> queryPointsByCity(String city);
    List<HeatMapPointBean> queryAllPoints();
}