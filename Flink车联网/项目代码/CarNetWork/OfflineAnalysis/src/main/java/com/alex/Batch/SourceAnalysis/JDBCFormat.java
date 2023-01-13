package com.alex.Batch.SourceAnalysis;

import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.typeutils.RowTypeInfo;

/**
 * @author Alex_liu
 * @create 2023-01-13 15:53
 * @Description 封装JDBCInputFormat方法，获取JDBCOutputFormat方法的抽象类
 *               分为有分批和没有分批两种情况
 */
public abstract  class JDBCFormat {

    public JDBCInputFormat getBatchJDBCInputFormat(String driverName, String url, String userName, String password, String inputSql, RowTypeInfo rowTypeInfo, int fetchSize) {

        return JDBCInputFormat.buildJDBCInputFormat()
                .setDrivername(driverName)
                .setDBUrl(url)
                .setUsername(userName)
                .setPassword(password)
                .setQuery(inputSql)
                .setRowTypeInfo(rowTypeInfo)
                .setFetchSize(fetchSize)
                .finish();
    }

    public JDBCInputFormat getJDBCInputFormat(String driverName, String url, String userName, String password, String inputSql, RowTypeInfo rowTypeInfo) {
        return JDBCInputFormat.buildJDBCInputFormat()
                .setDrivername(driverName)
                .setDBUrl(url)
                .setUsername(userName)
                .setPassword(password)
                .setQuery(inputSql)
                .setRowTypeInfo(rowTypeInfo)
                .finish();
    }

    public JDBCOutputFormat getBatchJDBCOutputFormat(String driverName, String url, String userName, String password, String outputSql, int batchInterval) {
        return JDBCOutputFormat.buildJDBCOutputFormat()
                .setDrivername(driverName)
                .setDBUrl(url)
                .setUsername(userName)
                .setPassword(password)
                .setQuery(outputSql)
                .setBatchInterval(batchInterval)
                .finish();
    }

    public JDBCOutputFormat getJDBCOutputFormat(String driverName, String url, String userName, String password, String outputSql) {
        return JDBCOutputFormat.buildJDBCOutputFormat()
                .setDrivername(driverName)
                .setDBUrl(url)
                .setUsername(userName)
                .setPassword(password)
                .setQuery(outputSql)
                .finish();
    }
}

