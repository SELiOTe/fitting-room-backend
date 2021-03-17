package com.seliote.fr.model.co;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Controller 响应基类
 * 注意，Controller 响应无法执行 JSR 380 校验
 *
 * @author seliote
 */
@Log4j2
@Data
public class Co<T> {
    // 请求成功
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "ok";
    // 401
    public static final Integer UNAUTHORIZED_CODE = -1;
    public static final String UNAUTHORIZED_MSG = "401 unauthorized";
    // 403
    public static final Integer FORBIDDEN_CODE = -2;
    public static final String FORBIDDEN_MSG = "403 forbidden";
    // 未知异常
    public static final Integer EXCEPTION_CODE = -1000;
    public static final String EXCEPTION_MSG = "unknown exception";
    // 404
    public static final Integer NO_HANDLER_FOUND_CODE = -1001;
    public static final String NO_HANDLER_FOUND_MSG = "404 not found";
    // API 访问频率异常
    public static final Integer FREQUENCY_EXCEPTION_CODE = -2000;
    public static final String FREQUENCY_EXCEPTION_MSG = "request too frequent";
    // Api 未知异常
    public static final Integer API_EXCEPTION_CODE = -3000;
    public static final String API_EXCEPTION_MSG = "api exception";
    // 工具类异常
    public static final Integer UTIL_EXCEPTION_CODE = -3001;
    public static final String UTIL_EXCEPTION_MSG = "util exception";
    // Redis 异常
    public static final Integer REDIS_EXCEPTION_CODE = -3002;
    public static final String REDIS_EXCEPTION_MSG = "redis exception";
    // Service 异常
    public static final Integer SERVICE_EXCEPTION_CODE = -3003;
    public static final String SERVICE_EXCEPTION_MSG = "service exception";
    // 数据异常
    public static final Integer DATA_EXCEPTION_CODE = -3004;
    public static final String DATA_EXCEPTION_MSG = "data exception";
    // 请求参数未通过 JSR 380 校验异常
    public static final Integer METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE = -4000;
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MSG = "request parameters error";
    // 分页请求参数中排序字段不在规定范围
    public static final Integer ORDER_BY_EXCEPTION_CODE = -4001;
    public static final String ORDER_BY_EXCEPTION_MSG = "order by request parameters error";

    // 响应状态码
    @JsonProperty("code")
    @NotNull
    private final Integer code;

    // 响应状态码描述
    @JsonProperty("msg")
    @NotNull
    private final String msg;

    // 响应数据
    @JsonProperty("data")
    @Valid
    private final T data;

    /**
     * 创建 Controller 响应基类
     *
     * @param code 响应状态码
     * @param msg  响应状态码描述
     * @param data 响应数据
     * @param <U>  响应数据泛型
     * @return Controller 响应基类
     */
    @NonNull
    public static <U> Co<U> cco(@NonNull int code, @NonNull String msg, @Nullable U data) {
        var co = new Co<>(code, msg, data);
        log.trace("Co<T>.cco(int, String, U) for: {}, {}, {}", code, msg, data);
        return co;
    }

    /**
     * 创建 Controller 响应基类
     *
     * @param code 响应状态码
     * @param msg  响应状态码描述
     * @return Controller 响应基类
     */
    @NonNull
    public static Co<?> cco(@NonNull int code, @NonNull String msg) {
        var co = Co.cco(code, msg, null);
        log.trace("Co<T>.cco(int, String) for: {}, {}", code, msg);
        return co;
    }

    /**
     * 创建 Controller 响应基类
     *
     * @param data 响应数据
     * @param <U>  响应数据泛型
     * @return Controller 响应基类
     */
    @NonNull
    public static <U> Co<U> cco(@Nullable U data) {
        var co = Co.cco(Co.SUCCESS_CODE, Co.SUCCESS_MSG, data);
        log.trace("Co<T>.cco(U) for: {}", data);
        return co;
    }
}
