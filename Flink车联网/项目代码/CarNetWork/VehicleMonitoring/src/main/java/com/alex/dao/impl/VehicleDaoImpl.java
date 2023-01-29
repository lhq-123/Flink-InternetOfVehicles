package com.alex.dao.impl;

import com.alex.constants.Constants;
import com.alex.dao.VehicleDao;
import com.alex.utils.DateUtil;
import com.alex.utils.JDBCUtil;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:33
 * @Description
 */
public class VehicleDaoImpl implements VehicleDao {
    @Override
    public void updateTestData(String param) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "UPDATE task set task_param = ? WHERE task_id = 3";
        Object[] params = new Object[]{"{\"startDate\":[\""+ DateUtil.getTodayDate()+"\"],\"endDate\":[\""+DateUtil.getTodayDate()+"\"],\""+ Constants.FIELD_CARS+"\":[\""+param+"\"]}"};
        jdbcUtil.executeUpdate(sql, params);
    }
}
