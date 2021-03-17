package com.seliote.fr.config.api;

import com.seliote.fr.annotation.stereotype.ApiComponent;
import com.seliote.fr.exception.FrequencyException;
import com.seliote.fr.service.RedisService;
import com.seliote.fr.util.CommonUtils;
import com.seliote.fr.util.TextUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * API 调用频率限制 AOP
 *
 * @author seliote
 */
@Log4j2
@Order(1)
@ApiComponent
@Aspect
public class ApiFrequency {

    private static final String redisNameSpace = "frequency";

    private final RedisService redisService;

    @Autowired
    public ApiFrequency(RedisService redisService) {
        this.redisService = redisService;
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * API 调用频率限制
     *
     * @param joinPoint AOP JoinPoint 对象
     */
    @Before("com.seliote.fr.config.api.ApiAop.api() && @annotation(com.seliote.fr.annotation.ApiFrequency)")
    public void apiFrequency(JoinPoint joinPoint) {
        Optional<String> uri = CommonUtils.getUri();
        if (uri.isEmpty()) {
            log.error("Frequency check error, uri is null");
            throw new FrequencyException("URI is empty");
        }
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var annotation = method.getAnnotation(com.seliote.fr.annotation.ApiFrequency.class);
        var identifier = (annotation.type() == ApiFrequencyType.ARG ?
                getArg(uri.get(), joinPoint, annotation) : getHeader(uri.get(), annotation));
        if (identifier.isEmpty()) {
            log.error("Frequency check error, identifier is empty for: {}", uri.get());
            // 过滤器在切面前执行，如果获取到为空说明代码有问题
            throw new FrequencyException("Identifier is empty");
        }
        // Token 或者参数可能会很长，所以 SHA-1 一下
        var sha1 = TextUtils.sha1(identifier.get());
        // 单位时长
        var unitSeconds = CommonUtils.time2Seconds(annotation.time(), annotation.unit());
        var redisKey = getRedisKey(uri.get(), sha1, unitSeconds);
        var current = frequency(redisKey, unitSeconds);
        var frequency = annotation.frequency();
        if (current <= frequency) {
            log.debug("Frequency pass for: {}, current: {}, identifier: {}, sha1: {}",
                    uri.get(), current, identifier.get(), sha1);
        } else {
            log.warn("Frequency too high: {}, current: {}, identifier: {}, sha1: {}",
                    uri.get(), current, identifier.get(), sha1);
            throw new FrequencyException("Frequency too high");
        }
    }

    /**
     * 获取请求参数中的标识符
     *
     * @param joinPoint  JoinPoint 对象注解
     * @param annotation @ApiFrequency 对象
     * @return 请求参数中的标识符
     */
    private Optional<String> getArg(String uri, JoinPoint joinPoint,
                                    com.seliote.fr.annotation.ApiFrequency annotation) {
        var args = joinPoint.getArgs();
        if (args == null || args.length != 1) {
            log.error("Args length incorrect: {}, {}", uri, args);
            return Optional.empty();
        }
        var arg = args[0];
        var keys = annotation.key().split(getKeySeparator());
        var identifiers = new String[keys.length];
        for (var i = 0; i < keys.length; ++i) {
            try {
                final var pd = new PropertyDescriptor(keys[i], arg.getClass());
                var result = pd.getReadMethod().invoke(arg);
                if (result == null) {
                    log.error("Argument getter return null: {}, argument: {}, getter: {}", uri, arg, keys[i]);
                    throw new FrequencyException("Argument getter return null");
                }
                identifiers[i] = result.toString();
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException exception) {
                log.error("Get identifier args error: {}, {}, exception: {}, message: {}, exception at: {}",
                        uri, arg, getClassName(exception), exception.getMessage(), keys[i]);
                return Optional.empty();
            }
        }
        return Optional.of(String.join(getIdentifierSeparator(), identifiers));
    }

    /**
     * 获取请求头中的标识符
     *
     * @param annotation @ApiFrequency 对象
     * @return 请求头中的标识符
     */
    private Optional<String> getHeader(String uri, com.seliote.fr.annotation.ApiFrequency annotation) {
        var keys = annotation.key().split(getKeySeparator());
        var identifiers = new String[keys.length];
        var servletAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletAttr != null) {
            var httpAttr = servletAttr.getRequest();
            for (var i = 0; i < keys.length; ++i) {
                var header = httpAttr.getHeader(keys[i]);
                if (header == null || header.length() <= 0) {
                    log.error("Header is null for: {}, header: {}", uri, keys[i]);
                    throw new FrequencyException("Get header return null");
                }
                identifiers[i] = header;
            }
        }
        return Optional.of(String.join(getIdentifierSeparator(), identifiers));
    }

    /**
     * 获取 Redis 中存储的 Key
     *
     * @param uri        请求 URI
     * @param identifier 本次请求的标识符
     * @param seconds    单位时间秒数
     * @return Redis 中存储的 Key
     */
    private String getRedisKey(String uri, String identifier, long seconds) {
        var now = Instant.now().getEpochSecond();
        // 计算单位起始时间，单位时间内访问次数限制
        var start = (now - (now % seconds)) + "";
        return redisService.formatKey(redisNameSpace, uri, identifier, start);
    }

    /**
     * 增加本次的频率并获取单位时间内的访问次数
     *
     * @param key     Redis Key
     * @param seconds 单位时间秒数
     * @return 访问次数
     */
    private long frequency(String key, long seconds) {
        if (!redisService.exists(key)) {
            redisService.setex(key, (int) seconds, "0");
        }
        return redisService.incr(key);
    }

    /**
     * 获取 Redis Key 的分隔符
     *
     * @return 分隔符
     */
    private String getKeySeparator() {
        return "&&";
    }

    /**
     * 获取标识符间的分隔符
     *
     * @return 分隔符
     */
    private String getIdentifierSeparator() {
        return ".";
    }
}
