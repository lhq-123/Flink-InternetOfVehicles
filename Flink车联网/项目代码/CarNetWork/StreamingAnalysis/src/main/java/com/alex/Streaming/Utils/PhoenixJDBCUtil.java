package com.alex.Streaming.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex_liu
 * @Create 2022-11-20
 * @Description
 *    phoenix的jdbc访问数据工具类
 */

public class PhoenixJDBCUtil {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static Statement statement = null;
    private static PreparedStatement ps = null;

    static {
        try {
            // load driver
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

            // get connection
            conn = DriverManager.getConnection("jdbc:phoenix:node01,node02,node03:2181");
            statement = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 创建schema
     * @param createSchema
     * @throws SQLException
     */
    public static void createSchema(String createSchema) throws SQLException {
            statement.executeUpdate(createSchema);
            conn.commit();
            close();
    }

    /**
     * @desc:创建表
     * @param createSql
     * @throws SQLException
     */
    public static void create(String createSql) throws SQLException {
            statement.executeUpdate(createSql);
            // 必须提交
            conn.commit();
            close();
    }

    /**
     * @desc:插入记录
     *  upsert into user(id, name, passwd) values(?, ?, ?)
     *  {"1", "张三", "111111"}
     * @param upsertSql
     */
    public static void  upsert(String upsertSql, String[] params) throws SQLException {
            ps = conn.prepareStatement(upsertSql);
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
            ps.executeUpdate();
            // 必须提交
            conn.commit();
            close();
    }

    /**
     * @desc 查询数据
     * @param querySql
     * @return
     */
    public static List<String[]> select(String querySql) throws SQLException {
        List<String[]> result = new ArrayList<>();
        rs = statement.executeQuery(querySql);
        ResultSetMetaData meta = rs.getMetaData();
        int colLength = meta.getColumnCount();
        List<String> colName = new ArrayList<>();
        for (int i = 1; i <= colLength; i++) {
            colName.add(meta.getColumnName(i));
        }

        String[] colArr;
        while (rs.next()) {
            colArr = new String[colLength];
            for (int i = 0; i < colLength; i++) {
                colArr[i] = rs.getString(colName.get(i));
            }
            result.add(colArr);
        }
        close();
        return result;
    }

    /**
     * @desc 批量插入数据
     * @param upsertSql
     * @param paramList
     */
    public static void  upsertBatch(String upsertSql, ArrayList<String[]> paramList) throws SQLException {
            ps = conn.prepareStatement(upsertSql);
            conn.setAutoCommit(false);
            paramList.forEach(params -> {
                try {
                    for (int i = 0; i < params.length; i++) {
                        ps.setString(i+1, params[i]);
                    }
                    ps.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            ps.executeBatch();
            // 必须提交
            conn.commit();
            close();
    }

    /**
     * @desc 删除
     * @param deleteSql
     * @throws SQLException
     */
    public static void delete(String deleteSql) throws SQLException {
        statement.executeUpdate(deleteSql);
        // 必须提交
        conn.commit();
        close();
    }

    /**
     * @desc:释放资源
     * @throws SQLException
     */
    public static void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (statement != null){
            statement.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}