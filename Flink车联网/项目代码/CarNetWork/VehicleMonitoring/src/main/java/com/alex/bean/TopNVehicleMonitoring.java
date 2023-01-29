package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:39
 * @Description  获取车流量排名前N的卡扣及卡扣明细数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopNVehicleMonitoring {
    private long taskId;
    private String monitorId;
    private int carCount;
    private String date;
    private String cameraId;
    private String car;
    private String actionTime;
    private String speed;
    private String roadId;
    
    public TopNVehicleMonitoring(long taskId, String date, String monitorId, String cameraId, String car, String actionTime, String speed, String roadId) {
        super();
        this.taskId = taskId;
        this.date = date;
        this.monitorId = monitorId;
        this.cameraId = cameraId;
        this.car = car;
        this.actionTime = actionTime;
        this.speed = speed;
        this.roadId = roadId;
    }

    public TopNVehicleMonitoring(long taskId, String monitorId, int carCount) {
        super();
        this.taskId = taskId;
        this.monitorId = monitorId;
        this.carCount = carCount;
    }
}
