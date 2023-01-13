package com.alex.Batch.SourceAnalysisT;

import com.alex.Batch.SourceAnalysis.JDBCFormat;
import com.alex.Batch.Util.DateUtil;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Alex_liu
 * @create 2023-01-13 17:14
 * @Description 统计完整数据与错误数据的占比率:按月、周、天维度计算总的数据准确率,相同特征计算方法类
 */
public class TotalDataRateT extends JDBCFormat implements Serializable {
    private Logger logger = LoggerFactory.getLogger(TotalDataRateT.class);
    private String day = DateUtil.getTodayDate();
    private final static String week = DateUtil.getNowWeekStart();
    private final static String month = DateUtil.getYearMonthDate() + "00";
    private String condition;

    public TotalDataRateT(String condition) {
        this.condition = condition;
    }

    /**
     * @desc: 任务执行主方法
     */
    public void executeTask() {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        Objects.requireNonNull(condition, "执行计算任务必要条件为空...");

        try {
            TotalDataRateT dataRateBatchTask = new TotalDataRateT(condition);
            JDBCInputFormat hiveJdbcInput = dataRateBatchTask.getHiveJdbcInput();
            DataSet<Row> hiveDataSet = env.createInput(hiveJdbcInput);
            DataSet<Row> hiveResultSet = convertHiveDataSet(hiveDataSet);
            JDBCOutputFormat mysqlOutput = dataRateBatchTask.getMysqlJdbcOutput();
            hiveResultSet.output(mysqlOutput);

            String[] taskNameArray = new String[]{"DayDataRateTask","WeekDataRateTask","MonthDataRateTask","UnKnowTask"};
            String taskName=setDynamicValue(condition, taskNameArray);

            env.execute(taskName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc: 通过flin jdbc获得hive数据
     * @return hive JDBCInputFormat
     */
    private JDBCInputFormat getHiveJdbcInput() {
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        String url = "jdbc:hive2://node03:10000/vehicle_ods";
        String userName = "root";
        String password = "123456";
        String hiveTableSrc = "vehicle_right";
        String hiveTableError = "vehicle_error";

        TypeInformation[] types = new TypeInformation[]{BasicTypeInfo.LONG_TYPE_INFO, BasicTypeInfo.LONG_TYPE_INFO};
        String[] columName = new String[]{"srcTotalNum", "errorTotalNum"};
        RowTypeInfo rowTypeInfo = new RowTypeInfo(types, columName);
//    int fetchSize = 10000;

        String sql = "SELECT srcTotalNum, errorTotalNum FROM (SELECT count(1) srcTotalNum FROM "+ hiveTableSrc+" where dt >= "+condition+") src , (SELECT count(1) errorTotalNum FROM "+ hiveTableError +" where dt >= "+condition+") error";
        logger.info("Method:getBatchJDBCInputFormat input params is : 'inputSql:{}'\t'rowTypeInfo:{}'",sql, rowTypeInfo.toString());
//        return getBatchJDBCInputFormat(driverName, url, userName, password, sql, rowTypeInfo, fetchSize)
        return getJDBCInputFormat(driverName, url, userName, password, sql, rowTypeInfo);
    }

    /**
     * @desc: 获得mysql输出jdbc
     * @return
     */
    private JDBCOutputFormat getMysqlJdbcOutput() {
        String mysqlDriver = "com.mysql.cj.jdbc.Driver";
        String mysqlUrl = "jdbc:mysql://Flink02:3306/vehicle_networking?characterEncoding=utf8&useSSL=false&serverTimezone=GMT&rewriteBatchedStatements=true";
        String mysqlUserName = "root";
        String mysqlPassword = "123456";
        String[] mysqlTableArray = new String[]{"vehicle_data_rate_day","vehicle_data_rate_week","vehicle_data_rate_month","UnKnowTable"};
        String mysqlTable = setDynamicValue(condition, mysqlTableArray);
        String[] sixthFieldArray = new String[]{"day","week","month","UnKnowName"};
        String sixthField = setDynamicValue(condition, sixthFieldArray);

        /** sql类型集合 */
//        int[] sqlTypes = new int[]{Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.FLOAT, Types.FLOAT, Types.VARCHAR, Types.VARCHAR};
//        int batchInterval = 1000;
        String sql = "insert into "+mysqlTable+"(series_no, src_total_num, error_src_total_num, data_accuracy, data_error_rate, "+sixthField+", process_date) values(?,?,?,?,?,?,?)";
        logger.info("Method:getBatchJDBCOutputFormat output params is : 'outputSql:{}'\t'sqlTypes:{}'", sql);
//        return getBatchJDBCOutputFormat(mysqlDriver,mysqlUrl,mysqlUserName,mysqlPassword, sql, sqlTypes, batchInterval)
        return getJDBCOutputFormat(mysqlDriver,mysqlUrl,mysqlUserName,mysqlPassword, sql);
    }

    /**
     * desc: 转换hive查询结果为最终计算结果
     * @param hiveDataSet
     * @return DataSet[resultRow]
     */
    private DataSet<Row> convertHiveDataSet(DataSet<Row> hiveDataSet) {
        MapOperator<Row, Row> mapRow = hiveDataSet.map(row -> {
            long srcTotalNum = Long.parseLong(row.getField(0).toString());
            long errorTotalNum = Long.parseLong(row.getField(1).toString());
            /** 计算结果:Row(随机id,srcTotalNum,errorTotalNum,srcTotalNum/(srcTotalNum+errorTotalNum),errorTotalNum/(srcTotalNum+errorTotalNum))*/
            Long dataAccuracy = srcTotalNum / (srcTotalNum + errorTotalNum);
            Row resultRow = new Row(7);
            resultRow.setField(0, UUID.randomUUID().toString());
            resultRow.setField(1, srcTotalNum);
            resultRow.setField(2, errorTotalNum);
            resultRow.setField(3, dataAccuracy);
            resultRow.setField(4, 1 - dataAccuracy);
            resultRow.setField(5, condition);
            resultRow.setField(6, DateUtil.getTodayDate());
            System.out.println(resultRow);
            return resultRow;
        });
        return mapRow;
    }

    /**
     * @desc: 根据传入条件，动态设置值
     * @param condition
     * @param setValues
     * @return 结果值
     */
    private String setDynamicValue(String condition, String[] setValues) {
        String resultValue;
        if (condition.equals(day)) {
            resultValue = setValues[0];
        } else if (condition.equals(week)) {
            resultValue = setValues[1];
        } else if (condition.equals(month)) {
            resultValue = setValues[2];
        } else {
            resultValue = setValues[3];
        }
        return resultValue;
    }
}
