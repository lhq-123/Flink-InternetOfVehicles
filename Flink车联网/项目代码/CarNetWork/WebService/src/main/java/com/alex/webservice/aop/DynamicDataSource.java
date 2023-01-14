package com.alex.webservice.aop;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:13
 * @Description
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSources = new InheritableThreadLocal<>();

    public static void setDataSource(String dataSource) {
        dataSources.set(dataSource);
    }

    public static void clearDataSource() {
        dataSources.remove();
    }
    // 检查数据绑定的数据源上下文信息
    @Override
    protected Object determineCurrentLookupKey() {
        return dataSources.get();
    }
}
