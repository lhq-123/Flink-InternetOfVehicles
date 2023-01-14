package com.alex.Batch.Test;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-15 10:28
 * @Description: Flink加载hive数据测试类
 */
public class FlinkHiveBatchTest {
    private final static Logger logger = LoggerFactory.getLogger(FlinkHiveBatchTest.class);

    private final static String DRIVE_NAME = "org.apache.hive.jdbc.HiveDriver";// jdbc驱动路径
    private final static String URL = "jdbc:hive2://node03:10000";// hive库地址+库名
    private final static String USER = "root";// 用户名
    private final static String PASSWORD = "123456";// 密码
    private final static String TABLE_NAME = "itcast_error";
    private final static String SQL = "select * from itcast_error";
    private final static String INSERT_SQL = "insert into itcast_test(json,yearmonth) values(?,?)";

    public static void main(String[] arg) {

        long time = System.currentTimeMillis();
        /**
         * 初始化环境
         */
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        try {
            TypeInformation[] types = new TypeInformation[]{BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO};
            String[] colName = new String[]{"json", "yearmonth"};
            RowTypeInfo rowTypeInfo = new RowTypeInfo(types, colName);
            JDBCInputFormat.JDBCInputFormatBuilder builder = JDBCInputFormat.buildJDBCInputFormat()
                    .setDrivername(DRIVE_NAME)
                    .setDBUrl(URL)
                    .setUsername(USER)
                    .setPassword(PASSWORD)
                    //.setFetchSize(10)
                    ;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
//            calendar.add(Calendar.DATE, -1); //用昨天产出的数据
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String d = sdf.format(calendar.getTime());

            JDBCInputFormat jdbcInputFormat = builder.setQuery(SQL + " where yearmonth='" + d + "' limit 10").setRowTypeInfo(rowTypeInfo).finish();
            DataSource<Row> rowlist = env.createInput(jdbcInputFormat);
//            DataSet<Row> temp = rowlist.map(value -> {
//                value.setField(0, value.getField(0).toString());
//                value.setField(1, value.getField(1).toString());
//                return value;
//            });

            JDBCOutputFormat mysqlOutPut = JDBCOutputFormat.buildJDBCOutputFormat().setDrivername("com.mysql.jdbc.Driver")
                    .setDBUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT&rewriteBatchedStatements=true")
                    .setUsername("root")
                    .setPassword("123456")
                    .setQuery(INSERT_SQL)
                    .setSqlTypes(new int[]{Types.LONGVARCHAR, Types.VARCHAR})
                    .finish();

            rowlist.output(mysqlOutPut);
//            temp.output(mysqlOutPut);
            env.execute("hive-" + TABLE_NAME + " sync");

            logger.warn("同步hive-" + TABLE_NAME + "完成，耗时:" + (System.currentTimeMillis() - time) / 1000 + "s");
        } catch (Exception e) {
            logger.error("同步hive-" + TABLE_NAME + "失败,时间戳:" + time + ",原因：" + e.toString());
        }
    }
}