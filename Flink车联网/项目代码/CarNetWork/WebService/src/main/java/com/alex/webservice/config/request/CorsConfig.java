package com.alex.webservice.config.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Alex_liu
 * @Date 2023-01-14 23:05
 * @Description 后台解决json跨域请求
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // todo 对所有地址都可以访问
        corsConfiguration.addAllowedOrigin("*");
        // todo 跨域请求头
        corsConfiguration.addAllowedHeader("*");
        // todo 关于请求方法
        corsConfiguration.addAllowedMethod("*");
        // todo 跨域请求 允许获得同一个 session
        // corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 配置可以访问的地址(路径)
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}