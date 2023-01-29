package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:38
 * @Description 卡扣信息及状态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringState {
    private long taskId;
    private String normalMonitorCount;//正常的卡扣个数
    private String normalCameraCount;//正常的摄像头个数
    private String abnormalMonitorCount;//不正常的卡扣个数
    private String abnormalCameraCount;//不正常的摄像头个数
    private String abnormalMonitorCameraInfos;//不正常的摄像头详细信息
}
