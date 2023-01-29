package com.alex.dao.factory;


import com.alex.dao.*;
import com.alex.dao.impl.*;

/**
 * @author Alex_liu
 * @create 2023-01-29 15:01
 * @Description Dao工厂类
 */
public class DaoFactory {
    public static TaskDao getTaskDao(){
        return new TaskDaoImpl();
    }
    public static MonitoringDao getMonitorDAO(){
        return new MonitoringDaoImpl();
    }

    public static RandomExtractDao getRandomExtractDAO(){
        return new RandomExtractDaoImpl();
    }

    public static VehicleTrackDao getCarTrackDAO(){
        return new VehicleTrackDaoImpl();
    }

    public static VehicleDao getWithTheCarDAO(){
        return new VehicleDaoImpl();
    }

    public static AreaDao getAreaDao() {
        return  new AreaDaoImpl();

    }

}
