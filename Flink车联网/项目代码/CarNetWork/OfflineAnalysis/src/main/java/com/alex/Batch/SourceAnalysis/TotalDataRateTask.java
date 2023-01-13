package com.alex.Batch.SourceAnalysis;

import com.alex.Batch.SourceAnalysis.TotalDataRate;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.types.Row;

/**
 * @author Alex_liu
 * @create 2023-01-13 16:56
 * @Description 原始数据准确率分析任务
 */
public class TotalDataRateTask {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        try {
            TotalDataRate totalDataRate = new TotalDataRate();
            JDBCInputFormat hiveJdbcInput = totalDataRate.getHiveJdbcInput();
            DataSet<Row> hiveDataSet = env.createInput(hiveJdbcInput);
            DataSet<Row> hiveResultSet = totalDataRate.convertHiveDataSet(hiveDataSet);
            JDBCOutputFormat mysqlOutput = totalDataRate.getMysqlJdbcOutput();
            // hive结果数据输出到mysql结果表中
            hiveResultSet.output(mysqlOutput);
            env.execute("com.alex.Batch.SourceAnalysis.TotalDataRateTask");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
