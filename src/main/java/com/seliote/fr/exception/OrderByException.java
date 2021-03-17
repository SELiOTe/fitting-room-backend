package com.seliote.fr.exception;

/**
 * 可排序分页查询中 orderBy 参数存在问题时抛出
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public class OrderByException extends ApiException {

    public OrderByException() {
        super();
    }

    public OrderByException(String message) {
        super(message);
    }

    public OrderByException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderByException(Throwable cause) {
        super(cause);
    }

    protected OrderByException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
