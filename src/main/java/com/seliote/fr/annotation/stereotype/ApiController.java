package com.seliote.fr.annotation.stereotype;

import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

/**
 * API 上下文 @Controller 注解
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Controller
public @interface ApiController {

    String value() default "";
}
