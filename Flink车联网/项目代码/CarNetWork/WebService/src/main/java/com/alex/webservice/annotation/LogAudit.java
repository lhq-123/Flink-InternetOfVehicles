package com.alex.webservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:16
 * @Description 接口日志审计
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAudit {
    // 初始化，赋默认值
    String value() default "";
}
