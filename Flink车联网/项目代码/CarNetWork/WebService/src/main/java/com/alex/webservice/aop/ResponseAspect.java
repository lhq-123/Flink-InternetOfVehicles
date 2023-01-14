package com.alex.webservice.aop;

import com.alex.webservice.annotation.AutoResponse;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.joda.time.DateTime;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @author Alex_liu
 * @create 2023-01-14 13:22
 * @Description http响应切面实现
 */

@Slf4j
@Aspect
@Component
public class ResponseAspect extends AbstractAspect{
    // 定义变量响应map
    private Map<String, AutoResponse> autoResponseMap = Maps.newConcurrentMap();
    // 定义切入点
    @Pointcut("@annotation(com.alex.webservice.annotation.AutoResponse)")
    public void httpResponse() { }

    /**
     * @desc 进入切入点中执行此方法,当被调用的Controller执行是通知切入点执行
     * @param proceedingJoinPoint
     * @return
     */
    @Around("httpResponse()")
    public Object handlerController(ProceedingJoinPoint proceedingJoinPoint) {
        // 根据执行切入点获得被注解修饰的方法
        AutoResponse autoResponse = getMethodAnnotation(proceedingJoinPoint);
        Map<String, Object> res = new HashMap<>(2);
        Map<String, Object> statusMap = new HashMap<>(4);
        try {
            //获取方法的执行结果
            Object proceed = proceedingJoinPoint.proceed();
            if(proceed == null
                    && !(autoResponse == null ? false : autoResponse.allowNull())){
                statusMap.put("isSuccess", false);
                statusMap.put("msg", "请求失败。");
                statusMap.put("code", autoResponse == null ? "500" : autoResponse.errorCode());
                statusMap.put("details", "返回值为null。");
                log.warn(String.format("请求 %s 成功，但结果集为null! ", super.getMethodAnnotation(proceedingJoinPoint)));
            }else{
                statusMap.put("isSuccess", true);
                statusMap.put("msg", "请求成功。");
                statusMap.put("code", autoResponse == null ? "200" : autoResponse.successCode());
                res.put("data", proceed);
            }
        }  catch (Throwable throwable) {
            statusMap.put("isSuccess", false);
            statusMap.put("msg", "请求失败。");
            statusMap.put("code", autoResponse == null ? "500" : autoResponse.errorCode());
            statusMap.put("details", throwable.getMessage());
            log.error(String.format("请求 %s 失败! ", super.getMethodAnnotation(proceedingJoinPoint)), throwable);
        }
        statusMap.put("timestamp", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        res.put("status", statusMap);
        return res;
    }
    // 获得被添加注解方法，并返回响应注解
    private AutoResponse getMethodAnnotation(ProceedingJoinPoint proceedingJoinPoint){
        // 获得切入点实例对象
        Signature signature = proceedingJoinPoint.getSignature();
        // 获得切入点方法名称
        String methodName = signature.getName();
        // 请求方法全部参数的类型
        Class[] argClass = ((MethodSignature) signature).getParameterTypes();
        // 设置自动响应map的key为方法名称$[请求参数类的简称组成的集合]
        String key = String.format("%s$%s", methodName,
                Arrays.stream(argClass).map(Class::getSimpleName).collect(Collectors.toList()));

        AutoResponse autoResponse = autoResponseMap.get(key);
        if(autoResponse == null){
            try {
                Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
                Method method = clazz.getMethod(methodName, argClass);

                // 判断是否存在@AutoResponse注解
                if (method.isAnnotationPresent(AutoResponse.class)) {
                    autoResponse = method.getAnnotation(AutoResponse.class);
                    autoResponseMap.put(key, autoResponse);
                }
            } catch (Exception e) {
                log.error(null, e);
            }
        }

        return autoResponse;
    }
}
