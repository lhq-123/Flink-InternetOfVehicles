package com.alex.webservice.service.impl;

import com.alex.webservice.dao.impl.HiveJdbcDao;
import com.alex.webservice.service.HiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/16 1:37
 * @Description TODO 编写hive测试接口，service实现类
 */
@Service
public class HiveServiceImpl implements HiveService {

    @Autowired
    private HiveJdbcDao hiveJdbcDao;

    @Override
    public Long totalNum() {
        Long total = hiveJdbcDao.getJdbcTemplate().queryForObject("select count(1) totalNum from vehicle_src", Long.class);
        return total;
    }
}