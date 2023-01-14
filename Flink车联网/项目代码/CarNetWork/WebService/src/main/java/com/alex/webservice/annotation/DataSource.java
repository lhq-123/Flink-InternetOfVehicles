package com.alex.webservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:12
 * @Description
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSource {
    //value成员变量，应为注解修饰类型为“Method”，即日定义注解内容为value方法，default是设置默认值
    String value() default MYSQL;
    // 定义mysql数据源key=value
    String MYSQL = "mysqlDataSource";
    // 定义hive数据源key=value
    String HIVE = "hiveDataSource";
}
