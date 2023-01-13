package com.alex.Batch.SourceAnalysis;

import com.alex.Batch.Util.DateUtil;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author Alex_liu
 * @create 2023-01-13 16:41
 * @Description 计算总的数据准确率
 */
public class TotalDataRate extends JDBCFormat {
    private Logger logger = LoggerFactory.getLogger(TotalDataRate.class);
    public JDBCInputFormat getHiveJdbcInput() {
        //hive connect info
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        String url = "jdbc:hive2://node03:10000";
        String userName = "root";
        String password = "123456";
        String hiveTableSrc = "vehicle_right";
        String hiveTableError = "vehicle_error";

        //查询结果返回字段类型
        TypeInformation[] types = new TypeInformation[]{BasicTypeInfo.LONG_TYPE_INFO, BasicTypeInfo.LONG_TYPE_INFO};
        //查询结果返回字段
        String[] columName = new String[]{"srcTotalNum", "errorTotalNum"};
        RowTypeInfo rowTypeInfo = new RowTypeInfo(types, columName);
        //每批次执行数据条数
//    String fetchSize = 10000;

        //hive查询作为输入数据：vehicle_right、vehicle_error,再进行计算
        String sql = "SELECT srcTotalNum, errorTotalNum FROM (SELECT count(1) srcTotalNum FROM "+hiveTableSrc+") src, (SELECT count(1) errorTotalNum FROM "+hiveTableError+") error";
        logger.info("Method:getBatchJDBCInputFormat input params is : 'inputSql:{}'\t'rowTypeInfo:{}'",sql, rowTypeInfo.toString());
        return getJDBCInputFormat(driverName, url, userName, password, sql, rowTypeInfo);
    }

    /**
     * @desc:获得mysql输出jdbc
     * @return JDBCOutputFormat
     */
    public JDBCOutputFormat getMysqlJdbcOutput() {
        //mysql连接信息
        String mysqlDriver = "com.mysql.cj.jdbc.Driver";
        String mysqlUrl = "jdbc:mysql://Flink02:3306/vehicle_networking?characterEncoding=utf8&useSSL=false&serverTimezone=GMT&rewriteBatchedStatements=true";
        String mysqlUserName = "root";
        String mysqlPassword = "123456";
        String mysqlTableDataRate = "vehicle_data_rate";
        String sql = "insert into "+mysqlTableDataRate+"(series_no, src_total_num, error_src_total_num, data_accuracy, data_error_rate, process_date) values(?,?,?,?,?,?)";
        logger.info("Method:getBatchJDBCOutputFormat output params is : 'outputSql:{}'", sql);
        return getJDBCOutputFormat(mysqlDriver,mysqlUrl,mysqlUserName,mysqlPassword, sql);
    }

    /**
     * desc:转换hive查询结果为最终计算结果
     * @param hiveDataSet
     * @return DataSet[resultRow]
     */
    public DataSet<Row> convertHiveDataSet(DataSet<Row> hiveDataSet) {
        hiveDataSet.map(row -> {
            long srcTotalNum = Long.parseLong(row.getField(0).toString());
            long errorTotalNum = Long.parseLong(row.getField(1).toString());
            //计算结果:Row(随机id,srcTotalNum,errorTotalNum,srcTotalNum/(srcTotalNum+errorTotalNum),errorTotalNum/(srcTotalNum+errorTotalNum))
            Long dataAccuracy =  srcTotalNum / (srcTotalNum + errorTotalNum);
            Row resultRow = new Row(6);
            resultRow.setField(0, UUID.randomUUID().toString());
            resultRow.setField(1, srcTotalNum);
            resultRow.setField(2, errorTotalNum);
            resultRow.setField(3, dataAccuracy);
            resultRow.setField(4, 1 - dataAccuracy);
            resultRow.setField(5, DateUtil.getTodayDate());
            return resultRow;
        });
        return hiveDataSet;
    }
}
