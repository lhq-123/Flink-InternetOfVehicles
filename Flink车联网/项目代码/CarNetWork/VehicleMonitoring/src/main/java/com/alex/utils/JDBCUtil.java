package com.alex.utils;

import com.alex.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * @author Alex_liu
 * @create 2023-01-28 21:51
 * @Description JDBC工具类
 *   在代码中，是不能出现任何hard code（硬编码）的字符，这些东西，都需要通过常量来封装和使用
 */
public class JDBCUtil {

    private final static Logger logger = LoggerFactory.getLogger(JDBCUtil.class);
    //TODO 1）在静态代码块中，直接加载数据库驱动
    /**
     * 加载驱动，不是直接简单的，使用com.mysql.jdbc.Driver就可以了
     * 之所以说，不要硬编码，他的原因就在于这里
     *
     * com.mysql.jdbc.Driver只代表了MySQL数据库的驱动
     * 那么，如果有一天，我们的项目底层的数据库要进行迁移，比如迁移到Oracle或者是DB2、SQLServer
     * 那么，就必须很费劲的在代码中，找，找到硬编码了com.mysql.jdbc.Driver的地方，然后改成其他数据库的驱动类的类名
     * 所以正规规范，是不允许硬编码的，那样维护成本很高
     *
     * 通常，我们都是用一个常量接口中的某个常量，来代表一个值
     * 然后在这个值改变的时候，只要改变常量接口中的常量对应的值就可以了
     *
     * 项目，要尽量做成可配置的，就是说，我们的这个数据库驱动，更进一步，也不只是放在常量接口中就可以了
     * 最好的方式，是放在外部的配置文件中，跟代码彻底分离
     */
    static {
        try {
            String driver = ConfigUtil.getProperty(Constants.JDBC_DRIVER);
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO 2）实现JDBC的单利化
    /**
     * 为什么要实现单例化呢？因为它的内部要封装一个简单的内部的数据库连接池
     * 为了保证数据库连接池有且仅有一份，所以就通过单例的方式
     * 保证JDBCHelper只有一个实例，实例中只有一份数据库连接池
     */
    private static JDBCUtil instance = null;

    /**
     * 获取单例
     * @return 单例
     */
    public static JDBCUtil getInstance() {
        if (instance == null){
            synchronized (JDBCUtil.class){
                if (instance == null){
                    instance = new JDBCUtil();
                }
            }
        }
        return instance;
    }

    private LinkedList<Connection> dataSource = new LinkedList<Connection>();

    //TODO 3）实现单例的过程中，创建唯一的数据库连接池
    /**
     * 私有化构造方法
     *
     * JDBCUtil在整个程序运行声明周期中，只会创建一次实例
     * 在这一次创建实例的过程中，就会调用JDBCHelper()构造方法
     * 此时，就可以在构造方法中，去创建自己唯一的一个数据库连接池
     */
    private JDBCUtil() {
        //获取连接池的大小，可以在配置文件中配置，灵活设定
        int dataSourceSize = ConfigUtil.getInteger(Constants.JDBC_DATASOURCE_SIZE);
        //创建指定数量的数据库连接，并放入数据库连接池中
        for (int i = 0; i< dataSourceSize; i++){
            String url = null;
            String user = null;
            String password = null;
            boolean local = ConfigUtil.getBoolean(Constants.SPARK_LOCAL);
            if(local) {
                url = ConfigUtil.getProperty(Constants.JDBC_URL);
                user = ConfigUtil.getProperty(Constants.JDBC_USER);
                password = ConfigUtil.getProperty(Constants.JDBC_PASSWORD);
            } else {
                url = ConfigUtil.getProperty(Constants.JDBC_URL_PROD);
                user = ConfigUtil.getProperty(Constants.JDBC_USER_PROD);
                password = ConfigUtil.getProperty(Constants.JDBC_PASSWORD_PROD);
            }
            if(local) {
                url = ConfigUtil.getProperty(Constants.JDBC_URL);
                user = ConfigUtil.getProperty(Constants.JDBC_USER);
                password = ConfigUtil.getProperty(Constants.JDBC_PASSWORD);
            } else {
                url = ConfigUtil.getProperty(Constants.JDBC_URL_PROD);
                user = ConfigUtil.getProperty(Constants.JDBC_USER_PROD);
                password = ConfigUtil.getProperty(Constants.JDBC_PASSWORD_PROD);
            }

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                //向链表头中放入一个元素
                dataSource.push(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 4）提供获取数据库连接的方法
    /**
     * 有可能，你去获取的时候，这个时候，连接都被用光了，你暂时获取不到数据库连接
     * 所以我们要自己编码实现一个简单的等待机制，去等待获取到数据库连接
     */
    public synchronized Connection getConnection() {
        while(dataSource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //检索并移除此列表的头元素(第一个元素)
        return dataSource.poll();
    }

    //TODO 5）封装数据库增删改查的方法
    /**
     * 执行增删改SQL语句，返回影响的行数
     * @param sql
     * @param params
     * @return 影响的行数
     */
    public int executeUpdate(String sql, Object[] params){
        int rtn = 0;
        Connection conn = null;
        PreparedStatement ps= null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            if (params != null && params.length > 0){
                for (int i = 0; i < params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
            }
            rtn = ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            logger.error("sql执行出现异常:"+e.getMessage());
        } finally {
            if (conn != null){
                dataSource.push(conn);
            }
        }
        return rtn;
    }

    /**
     * 执行查询SQL语句的方法
     * @param sql
     * @param params
     * @param callback
     */
    public void executeQuery(String sql, Object[] params,
                             QueryCallback callback) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            if(params != null && params.length > 0) {
                for(int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
            callback.process(rs);
        } catch (Exception e) {
            logger.error("sql执行出现异常:"+ e.getMessage());
        } finally {
            if(conn != null) {
                dataSource.push(conn);
            }
        }
    }

    /**
     * 静态内部类：查询回调接口，处理查询结果
     */
    public static interface QueryCallback {
        void process(ResultSet rs) throws Exception;
    }

    //TODO 6）封装批量执行SQL的方法

    /**
     * 批量执行SQL语句，是JDBC中的一个高级功能
     * 默认情况下，每次执行一条SQL语句，就会通过网络连接，向MySQL发送一次请求
     *
     * 但是，如果在短时间内要执行多条结构完全一模一样的SQL，只是参数不同
     * 虽然使用PreparedStatement这种方式，可以只编译一次SQL，提高性能，但是，还是对于每次SQL
     * 都要向MySQL发送一次网络请求
     *
     * 可以通过批量执行SQL语句的功能优化这个性能
     * 一次性通过PreparedStatement发送多条SQL语句，比如100条、1000条，甚至上万条
     * 执行的时候，也仅仅编译一次就可以
     * 这种批量执行SQL语句的方式，可以大大提升性能
     * @param sql
     * @param paramsList
     * @return 每条SQL语句影响的行数
     */
    public int[] executeBatch(String sql, List<Object[]> paramsList) {
        int[] rtn = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            // 第一步：使用Connection对象，取消自动提交
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            // 第二步：使用PreparedStatement.addBatch()方法加入批量的SQL参数
            if(paramsList != null && paramsList.size() > 0) {
                for(Object[] params : paramsList) {
                    for(int i = 0; i < params.length; i++) {
                        pstmt.setObject(i + 1, params[i]);
                    }
                    pstmt.addBatch();
                }
            }
            // 第三步：使用PreparedStatement.executeBatch()方法，执行批量的SQL语句
            rtn = pstmt.executeBatch();
            // 最后一步：使用Connection对象，提交批量的SQL语句
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                dataSource.push(conn);
            }
        }
        return rtn;
    }
}
