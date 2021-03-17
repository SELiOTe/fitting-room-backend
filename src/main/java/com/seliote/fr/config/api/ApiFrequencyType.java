package com.seliote.fr.config.api;

/**
 * API 调用频率限制注解类型
 *
 * @author seliote
 */
public enum ApiFrequencyType {
    // 请求参数，如果为该类型那么方法有且只能有一个参数
    ARG,
    // 请求头
    HEADER
}
