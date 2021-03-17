package com.seliote.fr.exception;

/**
 * Service 层返回数据存在问题时抛出
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public class ServiceException extends ApiException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
