package com.seliote.fr.annotation;

import com.seliote.fr.config.api.ApiFrequencyType;
import com.seliote.fr.config.auth.TokenFilter;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * API 调用频率限制注解，配合切面使用
 * 要求注解的方法有且只能有一个参数
 * 默认值为取 Token 请求头，每分钟五次
 *
 * @author seliote
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFrequency {

    // 类型，表示判断频率的值需要从哪个字段取，有先后顺序
    ApiFrequencyType type() default ApiFrequencyType.HEADER;

    // 判断频率使用的值，多个参数使用 && 连接
    String key() default TokenFilter.TOKEN_HEADER;

    // API 最大频率
    int frequency() default 5;

    // 时间
    long time() default 1;

    // 时间单位
    ChronoUnit unit() default ChronoUnit.MINUTES;
}
