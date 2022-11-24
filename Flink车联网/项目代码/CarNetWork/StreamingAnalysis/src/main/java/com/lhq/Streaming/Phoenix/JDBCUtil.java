package com.lhq.Streaming.Phoenix;

import com.lhq.Streaming.Utils.ConfigLoader;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liu
 * @Create 2022-11-20
 * @Description
 *    实现行程样本总数分析结果写入mysql中的t_sample_result表
 */

public class JDBCUtil {

    // 建立数据库连接池
    private static Connection connection = null;
    private static PreparedStatement ps = null;

    static {
        try {
            String jdbcDriver = ConfigLoader.get("jdbc.driver");
            String url = ConfigLoader.get("jdbc.url");
            String user = ConfigLoader.get("jdbc.user");
            String pwd = ConfigLoader.get("jdbc.password");
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 批量执行sql
     * @param sql
     * @param paramsList
     * @return 批处理行数
     */
    public static int[] executeBatch(String sql, List<ArrayList<Object>> paramsList) throws SQLException {
        int[] rows = null;
        ps = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        try {
            int countRow = 0;
            if (!paramsList.isEmpty()) {
                for (ArrayList params:paramsList) {
                    for (int i = 0;i < params.size(); i++) {
                        ps.setObject(i + 1, params.get(i));
                    }
                    ps.addBatch();
                    countRow += 1;
                    if (countRow > 0 && countRow % 1000 == 0) {
                        ps.executeBatch();
                        ps.clearBatch();
                        connection.commit();
                    }
                }
                rows = ps.executeBatch();
                ps.clearBatch();
                connection.commit();
            }
            close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return rows;
    }

    /**
     * @desc:单条执行sql
     * @param sql
     * @param params
     */
    public static void executeInsert(String sql, ArrayList<Object> params) throws SQLException {
        ps = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        try {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.execute();
            connection.commit();
            close();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    /**
     * @desc 关闭连接
     * @throws SQLException
     */
    public static void close() throws SQLException {
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}