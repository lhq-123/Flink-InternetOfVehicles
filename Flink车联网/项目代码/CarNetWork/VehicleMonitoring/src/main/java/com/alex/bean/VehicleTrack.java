package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:37
 * @Description 车辆轨迹信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTrack {
    private long taskId;
    private String date;
    private String car;
    private String track;
}
