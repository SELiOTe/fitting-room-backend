package com.seliote.fr.service.impl;

import com.seliote.fr.exception.RedisException;
import com.seliote.fr.service.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisDataException;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * Redis Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class RedisServiceImpl implements RedisService {

    public final String separator;

    private final String namespace;
    private final JedisPool jedisPool;

    private final String success = "OK";

    @Autowired
    public RedisServiceImpl(@Value("${redis.host}") String host,
                            @Value("${redis.port}") int port,
                            @Value("${redis.user}") String user,
                            @Value("${redis.password}") String password,
                            @Value("${redis.separator}") String separator,
                            @Value("${redis.namespace}") String namespace,
                            @Value("${redis.max-total}") int maxTotal,
                            @Value("${redis.max-idle}") int maxIdle,
                            @Value("${redis.min-idle}") int minIdle,
                            @Value("${redis.max-wait}") long maxWait,
                            @Value("${redis.min-evict}") long minEvict) {
        var jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvict);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPool = new JedisPool(jedisPoolConfig, host, port, user, password);
        this.separator = separator;
        this.namespace = namespace;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    public void destroy() {
        jedisPool.close();
        log.info("Destroy redis service and release redis pool");
    }

    @Override
    public void set(String key, String value) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.set(key, value);
            if (!result.equalsIgnoreCase(success)) {
                log.error("Error when RedisServiceImpl.set(String, String) for: {}, {}, result: {}",
                        key, value, result);
                throw new RedisException(result);
            }
            log.trace("RedisServiceImpl.set(String, String) success for: {}, {}", key, value);
        }
    }

    @Override
    public void setex(String key, int seconds, String value) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.setex(key, seconds, value);
            if (!result.equalsIgnoreCase(success)) {
                log.error("Error when RedisServiceImpl.setex(String, int, String) for: {}, {}, {}, result {}",
                        key, seconds, value, result);
                throw new RedisException(result);
            }
            log.trace("RedisServiceImpl.setex(String, int, String) success for: {}, {}, {}", key, seconds, value);
        }
    }

    @Override
    public Optional<String> get(String key) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.get(key);
            log.trace("RedisServiceImpl.get(String) for: {}, result: {}", key, result);
            return Optional.ofNullable(result);
        }
    }

    @Override
    public long del(String key) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.del(key);
            if (result == null) {
                log.error("Error when RedisServiceImpl.del(String) for: {}, result is null", key);
                throw new RedisException();
            }
            log.trace("RedisServiceImpl.del(String) for {} success, result: {}", key, result);
            return result;
        }
    }

    @Override
    public long incr(String key) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.incr(key);
            if (result == null) {
                log.error("Error when RedisServiceImpl.incr(String) for: {}, result is null", key);
                throw new RedisException();
            }
            log.trace("RedisServiceImpl.incr(String) success for: {}, result: {}", key, result);
            return result;
        } catch (JedisDataException exception) {
            log.error("RedisServiceImpl.incr(String) occur type error for: {}, message: {}",
                    key, exception.getMessage());
            throw new RedisException(exception.getMessage());
        }
    }

    @Override
    public void expire(String key, int seconds) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.expire(key, seconds);
            if (result == null || result != 1) {
                log.error("Error when RedisServiceImpl.expire(String, int) for: {}, {}, result: {} not equals 1",
                        key, seconds, result);
                throw new RedisException();
            }
            log.trace("RedisServiceImpl.expire(String, int) for: {}, {} success, result: {}",
                    key, seconds, result);
        }
    }

    @Override
    public boolean exists(@NotNull String key) {
        try (var jedis = jedisPool.getResource()) {
            var result = jedis.exists(key);
            if (result == null) {
                log.error("Error when RedisServiceImpl.exists(String) for: {}, result is null", key);
                throw new RedisException();
            }
            log.trace("RedisServiceImpl.exists(String) success for: {}, result: {}", key, result);
            return result;
        }
    }

    @Override
    public String formatKey(String... keys) {
        var sb = new StringBuilder();
        sb.append(namespace).append(separator);
        for (var key : keys) {
            sb.append(key).append(separator);
        }
        var result = sb.substring(0, sb.length() - 1);
        log.trace("RedisServiceImpl.formatKey(String...) for: {}, result: {}", Arrays.toString(keys), result);
        return result;
    }
}
