package com.seliote.fr.util;

import com.seliote.fr.exception.UtilException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型类型捕获，可用于 Bean 拷贝中含有泛型参数类型
 *
 * @author seliote
 */
@SuppressWarnings("unused")
public abstract class TypeReference<T> {

    private final Type type;

    /**
     * Protected 构造器，确保子类可构造
     */
    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new UtilException("Illegal TypeReference");
        }
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    /**
     * 获取捕获的类型
     *
     * @return 捕获的类型
     */
    public Type getType() {
        return type;
    }
}
