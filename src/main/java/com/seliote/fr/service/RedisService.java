package com.seliote.fr.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Redis Service
 *
 * @author seliote
 */
@Validated
public interface RedisService extends DisposableBean {

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值
     */
    void set(@NotNull String key, @NotNull String value);

    /**
     * 设置具有过期时间的键值
     *
     * @param key     键
     * @param seconds 过期秒数
     * @param value   值
     */
    void setex(@NotNull String key, @Min(0) int seconds, @NotNull String value);

    /**
     * 获取字符串类型数据，key 不存在时返回 null，值非字符串时返回错误
     *
     * @param key 键
     * @return 值
     */
    Optional<String> get(@NotNull String key);

    /**
     * 删除键值对,键不存在时将什么都不会做
     *
     * @param key 键
     * @return 删除的数量
     */
    long del(@NotNull String key);

    /**
     * 数值类型加一，如果给定的值不是数值类型将抛出异常，给定的键不存在时将置 0 并加 1
     *
     * @param key 键
     * @return 自增后的新值
     */
    long incr(@NotNull String key);

    /**
     * 设置键值对的过期时间，键已存在过期时间时将会进行更新,键不存在时将什么都不会做
     *
     * @param key     键
     * @param seconds 过期秒数
     */
    void expire(@NotNull String key, @Min(0) int seconds);

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return 存在返回 true，否则返回 false
     */
    boolean exists(@NotNull String key);

    /**
     * 格式化键，加上命名空间以及分隔符
     *
     * @param keys 需要格式化的键数组
     * @return 格式化后的键
     */
    @NotBlank
    String formatKey(String... keys);
}
