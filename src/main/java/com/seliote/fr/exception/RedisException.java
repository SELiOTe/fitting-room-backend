package com.seliote.fr.exception;

/**
 * Redis 操作存在异常
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public class RedisException extends ApiException {

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    protected RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
