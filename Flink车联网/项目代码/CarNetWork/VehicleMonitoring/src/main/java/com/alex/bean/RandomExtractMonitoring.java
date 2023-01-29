package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:38
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomExtractMonitoring {
    private long taskId;
    private String date;
    private String monitorId;
    private String cameraId;
    private String car;
    private String actionTime;
    private String speed;
    private String roadId;
}
