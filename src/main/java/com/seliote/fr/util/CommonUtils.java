package com.seliote.fr.util;

import com.seliote.fr.exception.UtilException;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 通用工具
 *
 * @author seliote
 */
@Log4j2
public class CommonUtils {

    /**
     * 获取 SecureRandom 对象
     *
     * @return SecureRandom 对象
     */
    @NonNull
    public static SecureRandom getSecureRandom() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            log.trace("CommonUtils.getSecureRandom() result: {}", random);
            return random;
        } catch (NoSuchAlgorithmException | NoSuchProviderException exception) {
            log.fatal("Can not create SecureRandom object, exception: {}, message: {}",
                    getClassName(exception), exception.getMessage());
            throw new UtilException(exception);
        }
    }

    /**
     * 获取当前请求 URI
     *
     * @return 当前请求的 URI，获取失败时返回 null
     */
    @NonNull
    public static Optional<String> getUri() {
        String uri = null;
        var servletAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletAttr != null) {
            var httpAttr = servletAttr.getRequest();
            uri = httpAttr.getRequestURI();
        }
        log.trace("CommonUtils.getUri() result: {}", uri);
        return Optional.ofNullable(uri);
    }

    /**
     * 获取请求的 IP
     *
     * @param httpServletRequest HttpServletRequest 对象
     * @return IP 地址
     */
    @NonNull
    public static Optional<String> getIp(@NonNull HttpServletRequest httpServletRequest) {
        var ip = httpServletRequest.getRemoteAddr();
        log.trace("CommonUtils.getIp(HttpServletRequest), ip: {}", ip);
        return Optional.ofNullable(ip);
    }

    /**
     * 将对应时间转换为秒
     *
     * @param time 时间
     * @param unit 时间单位
     * @return 秒
     */
    @NonNull
    public static long time2Seconds(long time, @NonNull TemporalUnit unit) {
        var duration = Duration.of(time, unit);
        var seconds = duration.toSeconds();
        log.trace("CommonUtils.time2Seconds(int, TemporalUnit) convert: {}, {}, to: {}", time, unit, seconds);
        return seconds;
    }
}
