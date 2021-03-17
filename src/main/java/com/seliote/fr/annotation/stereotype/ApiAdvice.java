package com.seliote.fr.annotation.stereotype;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.*;

/**
 * API 上下文 @ControllerAdvice 注解
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ControllerAdvice
public @interface ApiAdvice {

    String value() default "";
}
