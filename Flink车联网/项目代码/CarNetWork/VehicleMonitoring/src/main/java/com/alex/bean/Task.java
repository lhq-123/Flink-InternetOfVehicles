package com.alex.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:37
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private long taskId;
    private String taskName;
    private String createTime;
    private String startTime;
    private String finishTime;
    private String taskType;
    private String taskStatus;
    private String taskParams;
}
