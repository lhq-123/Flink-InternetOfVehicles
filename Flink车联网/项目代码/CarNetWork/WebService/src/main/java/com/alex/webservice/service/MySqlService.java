package com.alex.webservice.service;

import com.alex.webservice.bean.ElectronicFenceVinsBean;

import java.util.List;

/**
 * @author Alex_liu
 * @Date 2023-01-14 1:33
 * @Description  编写Mysql测试接口service层
 */
public interface MySqlService {
    // 分页查询电子围栏信息
    List<ElectronicFenceVinsBean> queryAll(int pageNo, int pageSize);
    // 查询电子围栏总数
    Long totalNum();
}