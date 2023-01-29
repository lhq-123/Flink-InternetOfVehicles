package com.alex.dao.impl;

import com.alex.bean.Area;
import com.alex.dao.AreaDao;
import com.alex.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:32
 * @Description
 */
public class AreaDaoImpl implements AreaDao {
    @Override
    public List<Area> findAreaInfo() {
        final List<Area> areas = new ArrayList<Area>();
        String sql = "SELECT * FROM area_info";
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        jdbcUtil.executeQuery(sql, null, new JDBCUtil.QueryCallback() {
            public void process(ResultSet rs) throws Exception {
                if(rs.next()) {
                    String areaId = rs.getString(1);
                    String areaName = rs.getString(2);
                    areas.add(new Area(areaId, areaName));
                }
            }
        });
        return areas;
    }
}
