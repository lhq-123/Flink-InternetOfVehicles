package com.alex.webservice.mapper;

import com.alex.webservice.bean.VehicleDataRateBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-14 2:00
 * @Description 数据正确率和错误率后台数据服务接口Mapper接口，对应xml文件名称
 */
@Repository
public interface VehicleDataRateMapper {
    // TODO @Param注解 :明确标识param中的值为某一个参数名称:pageNo
    List<VehicleDataRateBean> queryAll(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    Long totalNum();
}