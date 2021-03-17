package com.seliote.fr.annotation.stereotype;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * API 上下文 @Component 注解
 *
 * @author seliote
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ApiComponent {

    String value() default "";
}
