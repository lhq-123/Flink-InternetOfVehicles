package com.alex.webservice.dao.impl;

import com.alex.webservice.dao.VehicleViewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alex_liu
 * @Date 2023-01-14 15:17
 * @Description 
 */
@Repository
public class VehicleViewDaoImpl implements VehicleViewDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long totalNum() {
        Long totalNum = jdbcTemplate.queryForObject("select count(distinct(vin)) totalNum from vehicle_ods.vehicle_right", Long.class);
        return totalNum;
    }

    @Override
    public Long onlineNum() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
        String sql = "select count(distinct(vin)) onlineNum from vehicle_ods.vehicle_right where terminaltime >= '" + currentTime + "'";
        Long totalNum = jdbcTemplate.queryForObject(sql, Long.class);
        return totalNum;
    }

    @Override
    public Integer drivingNum() {
        long currentTimeMillis = System.currentTimeMillis();
        long aheadTimeMillis = currentTimeMillis - 30;
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis));
        String aheadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(aheadTimeMillis));
        String sql = "select count(distinct(vin)) drivingNum from vehicle_ods.vehicle_right where terminaltime >= '"+ aheadTime +"' and terminaltime <= '" + currentTime + "'";
        Integer totalNum = jdbcTemplate.queryForObject(sql, Integer.class);
        return totalNum;
    }
}
