package com.seliote.fr.exception;

/**
 * 数据异常时抛出
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public class DataException extends ApiException {
    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    protected DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
