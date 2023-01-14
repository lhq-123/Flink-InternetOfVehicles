package com.alex.webservice.mapper;

import com.alex.webservice.bean.ElectricFenceBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-14 2:04
 * @Description 电子围栏后台数据服务接口Mapper接口，对应xml文件名称
 */
@Repository
public interface ElectricFenceMapper {
    List<ElectricFenceBean> queryAll(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    Long totalNum();
}