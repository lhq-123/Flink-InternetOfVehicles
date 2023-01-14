package com.alex.webservice.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alex_liu
 * @create 2023-01-14 17:04
 * @Description 存放公共DataSource属性对象
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = false)
public class DataSourceProperties {
    private Map<String,String> mysql;
    private Map<String,String> hive;
    private Map<String,String> commonconfig;
}
