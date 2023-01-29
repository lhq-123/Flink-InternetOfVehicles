package com.alex.dao;

import com.alex.bean.Area;

import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:30
 * @Description
 */
public interface AreaDao {
    List<Area> findAreaInfo();
}
