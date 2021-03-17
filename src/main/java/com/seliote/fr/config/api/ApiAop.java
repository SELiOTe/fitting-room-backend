package com.seliote.fr.config.api;

import com.seliote.fr.annotation.stereotype.ApiComponent;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * API AOP
 *
 * @author seliote
 */
@Log4j2
@ApiComponent
public class ApiAop {

    @Autowired
    public ApiAop() {
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * API 切点
     */
    @Pointcut("execution(public * com.seliote.fr.controller..*.*(..))")
    public void api() {
    }
}
