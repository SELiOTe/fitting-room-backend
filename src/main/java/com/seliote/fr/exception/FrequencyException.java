package com.seliote.fr.exception;

/**
 * API 调用频率过高异常
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public class FrequencyException extends ApiException {
    public FrequencyException() {
        super();
    }

    public FrequencyException(String message) {
        super(message);
    }

    public FrequencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequencyException(Throwable cause) {
        super(cause);
    }

    protected FrequencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
