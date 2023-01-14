package com.alex.webservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Alex_liu
 * @create 2023-01-14 13:18
 * @Description
 */

@Aspect
@Component
public class WebLogAspect extends AbstractAspect{
    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    // 定义启动时间变量
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    // 定义方法方法名称变量
    ThreadLocal<String> method = new ThreadLocal<>();
    // 设置切入点
    @Pointcut("@annotation(com.alex.webservice.annotation.LogAudit)")
    public void webLog(){}
    // 进入切入点，最先执行此方法
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 根据http请求获得ip地址
        String ipAddr = getIpAddr(request);
        String method = request.getMethod();

        String methodAnnotation = getMethodAnnotation(joinPoint);
        if(methodAnnotation == null){
            Signature signature = joinPoint.getSignature();
            methodAnnotation = String.format("%s.%s", signature.getDeclaringTypeName(), signature.getName());
        }
        this.method.set(methodAnnotation);

        // 记录下请求内容
        logger.info(
                String.format("%s 接口：%s \n\t 请求url: %s \n\t 请求IP: %s \n\t 请求参数：%s",
                        method, methodAnnotation, request.getRequestURL().toString(),
                        ipAddr, Arrays.toString(joinPoint.getArgs()))
        );
    }

    // 切入点执行完成前，最后执行此方法
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容 以及耗时
        if(logger.isDebugEnabled()){
            logger.debug(String.format("接口[%s]执行耗时: %ss， 结果如下：\n\t %s" ,
                    method.get(), (System.currentTimeMillis() - startTime.get()) / 1000.0, ret));
        }else{
            logger.info(String.format("接口[%s]执行耗时: %ss"
                    , method.get(), (System.currentTimeMillis() - startTime.get()) / 1000.0));
        }
    }
}
