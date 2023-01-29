package com.alex.dao.impl;

import com.alex.bean.Task;
import com.alex.dao.TaskDao;
import com.alex.utils.JDBCUtil;

import java.sql.ResultSet;

/**
 * @author Alex_liu
 * @create 2023-01-29 14:33
 * @Description 任务管理Dao类
 */
public class TaskDaoImpl implements TaskDao {
    @Override
    public Task findTaskById(long taskId) {
        final Task task = new Task();
        String sql = "SELECT * FROM task WHERE task_id = ?";
        Object[] params = new Object[]{taskId};
        JDBCUtil jdbcUtil = JDBCUtil.getInstance();
        jdbcUtil.executeQuery(sql, params, new JDBCUtil.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                if(rs.next()) {
                    long taskId = rs.getLong(1);
                    String taskName = rs.getString(2);
                    String createTime = rs.getString(3);
                    String startTime = rs.getString(4);
                    String finishTime = rs.getString(5);
                    String taskType = rs.getString(6);
                    String taskStatus = rs.getString(7);
                    String taskParam = rs.getString(8);
                    task.setTaskId(taskId);
                    task.setTaskName(taskName);
                    task.setCreateTime(createTime);
                    task.setStartTime(startTime);
                    task.setFinishTime(finishTime);
                    task.setTaskType(taskType);
                    task.setTaskStatus(taskStatus);
                    task.setTaskParams(taskParam);
                }
            }
        });
        return task;
    }
}
