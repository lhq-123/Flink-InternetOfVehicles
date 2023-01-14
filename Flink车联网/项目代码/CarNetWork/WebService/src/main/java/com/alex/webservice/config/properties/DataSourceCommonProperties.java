package com.alex.webservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Alex_liu
 * @create 2023-01-14 17:10
 * @Description 创建连接池DataSource对象
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource.commonconfig", ignoreUnknownFields = false)
public class DataSourceCommonProperties {
    private int initialSize = 10;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private String filters;
    private String mapperLocations;
    private String typeAliasPackage;
}
