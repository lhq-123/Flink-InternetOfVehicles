package com.alex.webservice.config.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alex_liu
 * @create 2023-01-14 17:22
 * @Description Swagger UI配置类
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        // 创建API构建对象，并设置对象相关属性
        return new ApiInfoBuilder()
                .title("分析平台-数据接口Restful API文档")
                .description("本文档提供并展示系统所有对外接口的明细")
                .termsOfServiceUrl("")
                .version("2.0")
                .contact(new Contact("Alex的星途车联网大数据平台", "http://www.alexVehicle.cn/", ""))
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alex.webservice.controller"))
                .build();
    }
}
