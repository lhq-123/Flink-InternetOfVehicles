package com.alex.webservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:21
 * @Description http自动响应注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AutoResponse {
    /** 成功标识码 */
    String successCode() default "200";

    /** 失败标识码 */
    String errorCode() default "500";

    /** 自动识别 结果集大小 不能识别数组*/
    // boolean size() default false;

    boolean allowNull() default false;
}
