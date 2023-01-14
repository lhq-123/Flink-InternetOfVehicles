package com.alex.webservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alex_liu
 * @create 2023-01-14 18:27
 * @Description 后台数据服务SpringBoot启动类
 * @SpringBootApplication: 配置springboot快速启动类注解
 * @EnableSwagger2: 接口开发工具
 * @MapperScan: 扫描加载mybatis的接口的包路径
 */

@MapperScan("com.alex.webservice.mapper")
@EnableSwagger2
@MapperScan("com.alex.webservice.mapper")
@SpringBootApplication
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

}
