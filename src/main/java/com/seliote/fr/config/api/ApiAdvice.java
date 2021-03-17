package com.seliote.fr.config.api;

import com.seliote.fr.exception.*;
import com.seliote.fr.model.co.Co;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * Api Controller 未捕获异常处理器
 *
 * @author seliote
 */
@Log4j2
@com.seliote.fr.annotation.stereotype.ApiAdvice
public class ApiAdvice {

    @Autowired
    public ApiAdvice() {
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 未捕获 Exception 处理
     *
     * @param exception Exception 对象
     * @return 响应实体
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Co<?> handle(Exception exception) {
        log.error("ApiAdvice.handle(Exception) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.EXCEPTION_CODE, Co.EXCEPTION_MSG);
    }

    /**
     * 未捕获 NoHandlerFoundException 处理
     *
     * @param exception NoHandlerFoundException 对象
     * @return 响应实体
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public Co<?> handle(NoHandlerFoundException exception) {
        log.warn("ApiAdvice.handle(NoHandlerFoundException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.NO_HANDLER_FOUND_CODE, Co.NO_HANDLER_FOUND_MSG);
    }

    /**
     * 未捕获 FrequencyException 处理
     *
     * @param exception FrequencyException 对象
     * @return 响应实体
     */
    @ExceptionHandler({FrequencyException.class})
    @ResponseBody
    public Co<?> handle(FrequencyException exception) {
        log.warn("ApiAdvice.handle(FrequencyException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.FREQUENCY_EXCEPTION_CODE, Co.FREQUENCY_EXCEPTION_MSG);
    }

    /**
     * 未捕获 ApiException 处理
     *
     * @param exception ApiException 对象
     * @return 响应实体
     */
    @ExceptionHandler({ApiException.class})
    @ResponseBody
    public Co<?> handle(ApiException exception) {
        log.error("ApiAdvice.handle(ApiException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.API_EXCEPTION_CODE, Co.API_EXCEPTION_MSG);
    }

    /**
     * 未捕获 UtilException 处理
     *
     * @param exception UtilException 对象
     * @return 响应实体
     */
    @ExceptionHandler({UtilException.class})
    @ResponseBody
    public Co<?> handle(UtilException exception) {
        log.error("ApiAdvice.handle(UtilException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.UTIL_EXCEPTION_CODE, Co.UTIL_EXCEPTION_MSG);
    }

    /**
     * 未捕获 RedisException 处理
     *
     * @param exception RedisException 对象
     * @return 响应实体
     */
    @ExceptionHandler({RedisException.class})
    @ResponseBody
    public Co<?> handle(RedisException exception) {
        log.error("ApiAdvice.handle(RedisException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.REDIS_EXCEPTION_CODE, Co.REDIS_EXCEPTION_MSG);
    }

    /**
     * 未捕获 ServiceException 处理
     *
     * @param exception ServiceException 对象
     * @return 响应实体
     */
    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    public Co<?> handle(ServiceException exception) {
        log.error("ApiAdvice.handle(ServiceException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.SERVICE_EXCEPTION_CODE, Co.SERVICE_EXCEPTION_MSG);
    }

    /**
     * 未捕获 DataException 处理
     *
     * @param exception DataException 对象
     * @return 响应实体
     */
    @ExceptionHandler({DataException.class})
    @ResponseBody
    public Co<?> handle(DataException exception) {
        log.error("ApiAdvice.handle(DataException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.DATA_EXCEPTION_CODE, Co.DATA_EXCEPTION_MSG);
    }

    /**
     * 未捕获 MethodArgumentNotValidException 处理
     *
     * @param exception MethodArgumentNotValidException 对象
     * @return 响应实体
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Co<?> handle(MethodArgumentNotValidException exception) {
        log.warn("ApiAdvice.handle(MethodArgumentNotValidException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE, Co.METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MSG);
    }

    /**
     * 未捕获 OrderByException 处理
     *
     * @param exception OrderByException 对象
     * @return 响应实体
     */
    @ExceptionHandler({OrderByException.class})
    @ResponseBody
    public Co<?> handle(OrderByException exception) {
        log.warn("ApiAdvice.handle(OrderByException) occur: {}, message: {}",
                getClassName(exception), exception.getMessage());
        return Co.cco(Co.ORDER_BY_EXCEPTION_CODE, Co.ORDER_BY_EXCEPTION_MSG);
    }
}
