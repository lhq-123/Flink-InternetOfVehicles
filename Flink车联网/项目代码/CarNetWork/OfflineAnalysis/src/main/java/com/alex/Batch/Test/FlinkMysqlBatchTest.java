package com.alex.Batch.Test;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.io.jdbc.JDBCOutputFormat;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Alex_liu
 * @Date: 2023-01-15 10:28
 * @Description:Flink加载mysql数据测试类
 */
public class FlinkMysqlBatchTest {
    private final static Logger logger = LoggerFactory.getLogger(FlinkMysqlBatchTest.class);

    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USER = "root";
    private final static String PASSWORD = "123456";
    private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final static String SQL = "select name,age,country from user_info";
    private final static String INSERT_SQL = "insert into user_info (name,age,country) values(?,?,?)";

    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        try {
//            DataSet<Row> inputMysql = testJDBCRead(env);
//            inputMysql.print();
//            DataSet<Tuple3> mysqlDataSet = inputMysql.map(row ->
//                    new Tuple3<>(row.getField(0),row.getField(1),row.getField(2)));
//            mysqlDataSet.print();

            testJDBCWrite(env);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @desc Flink加载mysql数据，返回DataSet
     * @param env
     * @return DataSet<Row>
     */
    private static DataSet<Row> testJDBCRead(ExecutionEnvironment env) {
        DataSet<Row> inputMysql = env.createInput(JDBCInputFormat.buildJDBCInputFormat()
                // 数据库连接驱动名称
                .setDrivername(DRIVER_CLASS)
                // 数据库连接驱动名称
                .setDBUrl(URL)
                // 数据库连接用户名
                .setUsername(USER)
                // 数据库连接密码
                .setPassword(PASSWORD)
                // 数据库连接查询SQL
                .setQuery(SQL)
                // 字段类型,顺序个个数必须与SQL保持一致
                .setRowTypeInfo(new RowTypeInfo(BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO))
                .setFetchSize(10)
                .finish()
        );
        logger.warn("读取数据成功");
        return inputMysql;
    }

    /**
     * @desc Flink写数据到mysql
     * @param env
     * @throws Exception
     */
    private static void testJDBCWrite(ExecutionEnvironment env) throws Exception {
        // 生成测试数据，由于插入数据需要是Row格式，提前设置为Row
        List arrayList = new ArrayList<Row>();
        // arity:The number of fields in the Row
        Row row1 = new Row(3);
        row1.setField(0, "刘备");
        row1.setField(1, 30);
        row1.setField(2, "蜀");

        Row row2 = new Row(3);
        row2.setField(0, "曹操");
        row2.setField(1, 35);
        row2.setField(2, "魏");

        Row row3 = new Row(3);
        row3.setField(0, "孙权");
        row3.setField(1, 38);
        row3.setField(2, "吴");

        arrayList.add(row1);
        arrayList.add(row2);
        arrayList.add(row3);

        // 将集合数据转成DataSet
        DataSet dataSet = env.fromCollection(arrayList);
        // 使用JDBCOutputFormat，将数据写入到Mysql
        dataSet.output(JDBCOutputFormat.buildJDBCOutputFormat()
                // 数据库连接驱动名称
                .setDrivername(DRIVER_CLASS)
                // 数据库连接URL
                .setDBUrl(URL)
                // 数据库连接用户名
                .setUsername(USER)
                // 数据库连接密码
                .setPassword(PASSWORD)
                .setBatchInterval(10)
                // 数据库插入SQL
                .setQuery(INSERT_SQL)
                .finish());

        // 触发执行Job
        env.execute("Test JDBC Output");
        logger.warn("插入数据成功");
    }
}
