package com.alex.webservice.service.impl;

import com.alex.webservice.dao.impl.HiveJdbcDao;
import com.alex.webservice.service.VehicleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alex_liu
 * @commpany vehicle
 * @Date 2022/9/17 23:10
 * @Description 车辆可视化页面服务实现类
 */
@Service
public class VehicleViewServiceImpl implements VehicleViewService {

    @Autowired
    private HiveJdbcDao hiveJdbcDao;

    @Override
    public Long totalNum() {
        JdbcTemplate jdbcTemplate = hiveJdbcDao.getJdbcTemplate();
        Long totalNum = jdbcTemplate.queryForObject("select count(distinct(vin)) totalNum from vehicle_ods.vehicle_src", Long.class);
        return totalNum;
    }

    @Override
    public Long onlineNum() {
        JdbcTemplate jdbcTemplate = hiveJdbcDao.getJdbcTemplate();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "select count(distinct(vin)) onlineNum from vehicle_ods.vehicle_src where terminaltime >= '" + currentTime + "'";
        Long totalNum = jdbcTemplate.queryForObject(sql, Long.class);
        return totalNum;
    }

    @Override
    public Integer drivingNum() {
        JdbcTemplate jdbcTemplate = hiveJdbcDao.getJdbcTemplate();
        long currentTimeMillis = System.currentTimeMillis();
        long aheadTimeMillis = currentTimeMillis - 30;
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis));
        String aheadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(aheadTimeMillis));
        String sql = "select count(distinct(vin)) drivingNum from vehicle_ods.vehicle_src where terminaltime >= '"+ aheadTime +"' and terminaltime <= '" + currentTime + "'";
        Integer totalNum = jdbcTemplate.queryForObject(sql, Integer.class);
        return totalNum;
    }
}