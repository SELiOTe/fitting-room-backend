package com.seliote.fr.config.api;

import com.seliote.fr.annotation.stereotype.ApiComponent;
import com.seliote.fr.util.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * API 日志 AOP
 *
 * @author seliote
 */
@Log4j2
@Order(2)
@ApiComponent
@Aspect
public class ApiLogger {

    @Autowired
    public ApiLogger() {
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * Controller 请求与响应日志 Logger
     *
     * @param proceedingJoinPoint AOP ProceedingJoinPoint 对象
     * @return Controller 返回对象
     * @throws Throwable Controller 处理异常时抛出
     */
    @Around("com.seliote.fr.config.api.ApiAop.api()")
    public Object apiLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Optional<String> uri = CommonUtils.getUri();
        var args = proceedingJoinPoint.getArgs();
        log.info("Request: {}, args: {}", uri.orElse("null"), Arrays.toString(args));
        Object respObj = null;
        try {
            respObj = proceedingJoinPoint.proceed();
            return respObj;
        } finally {
            log.info("Response: {}, result: {}", uri, respObj);
        }
    }
}
