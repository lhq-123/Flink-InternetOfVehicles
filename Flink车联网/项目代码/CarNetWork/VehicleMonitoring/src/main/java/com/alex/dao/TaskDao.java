package com.alex.dao;

import com.alex.bean.Task;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:31
 * @Description 任务管理接口
 *   根据task的主键查询指定的任务
 */
public interface TaskDao {
    Task findTaskById(long taskId);
}
