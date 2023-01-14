package com.alex.webservice.aop;


import com.alex.webservice.annotation.LogAudit;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:18
 * @Description
 */

@Slf4j
public class AbstractAspect {
    protected String getMethodAnnotation(JoinPoint joinPoint){

        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        // 得到方法的参数的类型
        Class[] argClass = ((MethodSignature) signature).getParameterTypes();
        String annotationValue = null;
        try {
            // 获得当前访问的class
            Class<?> className = joinPoint.getTarget().getClass();

            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@LogAudit注解
            if (method.isAnnotationPresent(LogAudit.class)) {
                LogAudit annotation = method.getAnnotation(LogAudit.class);
                // 取出注解中的方法注释
                annotationValue = annotation.value();
            }

            if(annotationValue.length() < 1 && method.isAnnotationPresent(ApiOperation.class)){
                ApiOperation annotation = method.getAnnotation(ApiOperation.class);
                // 取出注解中的方法注释
                annotationValue = annotation.value();
            }
        } catch (Exception e) {
            log.error(null, e);
            e.printStackTrace();
        }
        return annotationValue;
    }

    /**
     * @desc 根据http请求获得请求的ip地址
     * @param request
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        // 得到http请求到，通过浏览器请求都会有header
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1") || "0:0:0:0:0:0:0:1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    log.debug("获取本机ip失败", e);
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
