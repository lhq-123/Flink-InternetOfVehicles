package com.alex.dao.impl;

import com.alex.bean.VehicleTrack;
import com.alex.dao.VehicleTrackDao;
import com.alex.utils.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:33
 * @Description
 */
public class VehicleTrackDaoImpl implements VehicleTrackDao {
    @Override
    public void insertBatchVehicleTrack(List<VehicleTrack> vehicleTracks) {
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        String sql = "INSERT INTO car_track VALUES(?,?,?,?)";
        List<Object[]> params = new ArrayList<>();
        for(VehicleTrack v : vehicleTracks){
            /**
             * 添加到车辆轨迹表中
             */
            params.add(new Object[]{v.getTaskId(),v.getDate(),v.getCar(),v.getTrack()});

            /**
             * 添加到段时间内的车辆信息表中
             */
//			 long taskId = c.getTaskId();
//			 String car = c.getCar();
//			 String track = c.getTrack();
//			 Map<String, String> timeAndMonitor = StringUtils.getKeyValuesFromConcatString(track, "\\|");
//			 List<CarInfoPer5M> insertList = new ArrayList<>();
//			 List<CarInfoPer5M> updateList = new ArrayList<>();
//			 for (Entry<String, String> entry : timeAndMonitor.entrySet()) {
//				 String monitorId = entry.getKey();
//				 String dateTime = entry.getValue();
//				 String timeRange = DateUtils.getRangeTime(dateTime);
//
//				 String sqlText = "SELECT * FROM monitor_range_time_car WHERE task_id = ? AND monitor_id = ? AND range_time = ?";
//			  	 Object[] selarams = new Object[]{taskId ,monitorId,timeRange};
//			  	 final CarInfoPer5M carInfoPer5M = new CarInfoPer5M();
//			  	 jdbcHelper.executeQuery(sqlText, selarams, new QueryCallback() {
//					@Override
//					public void process(ResultSet rs) throws Exception {
//						if(rs.next()){
//							carInfoPer5M.setCars(rs.getString(4));
//						}
//					}
//				});
//				carInfoPer5M.setTaskId(taskId);
//				carInfoPer5M.setMonitorId(monitorId);
//				carInfoPer5M.setRangeTime(timeRange);
//			  	 if(carInfoPer5M.getCars() != null){
//			  		 String cars = carInfoPer5M.getCars();
//			  		 cars += "|"+car+"="+dateTime;
//			  		 carInfoPer5M.setCars(cars);
//			  		 updateList.add(carInfoPer5M);
//			  	 }else{
//			  		 carInfoPer5M.setCars(car+"="+dateTime);
//			  		 insertList.add(carInfoPer5M);
//			  	 }
//			}
//
//			 String insertSQL = "INSERT INTO monitor_range_time_car VALUES(?,?,?,?)";
//			 List<Object[]> insertParams = new ArrayList<>();
//			 for (CarInfoPer5M carInfoPer5M : insertList) {
//				 insertParams.add(new Object[]{carInfoPer5M.getTaskId(),carInfoPer5M.getMonitorId(),carInfoPer5M.getRangeTime(),carInfoPer5M.getCars()});
//			 }
//			 jdbcHelper.executeBatch(insertSQL, insertParams);
//
//			 String updateSQL = "UPDATE monitor_range_time_car SET cars = ? WHERE task_id = ? AND monitor_id = ? AND range_time = ?";
//			 for (CarInfoPer5M carInfoPer5M : updateList) {
//				 Object[] updateParam = new Object[]{carInfoPer5M.getCars(),carInfoPer5M.getTaskId(),carInfoPer5M.getMonitorId(),carInfoPer5M.getRangeTime()};
//				 jdbcHelper.executeUpdate(updateSQL, updateParam);
//			 }
        }
        jdbcUtil.executeBatch(sql, params);
    }
}
