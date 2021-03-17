package com.seliote.fr.util;

import com.seliote.fr.exception.UtilException;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

/**
 * 反射相关工具类
 *
 * @author seliote
 */
@Log4j2
public class ReflectUtils {

    /**
     * 获取指定 Class 对象的泛型类型的全限定类名
     *
     * @param clazz Class 对象
     * @param <T>   Class 对象的泛型类型
     * @return Class 对象的泛型类型的全限定类名
     */
    @NonNull
    public static <T> String getClassName(@NonNull Class<T> clazz) {
        var name = clazz.getCanonicalName();
        log.trace("ReflectUtils.getClassName(Class<T>) for: {}, result: {}", clazz, name);
        return name;
    }

    /**
     * 获取指定对象的全限定类名
     *
     * @param object 对象
     * @param <T>    对象的泛型类型
     * @return 对象的全限定类名
     */
    @NonNull
    public static <T> String getClassName(@NonNull T object) {
        var name = ReflectUtils.getClassName(object.getClass());
        log.trace("ReflectUtils.getClassName(T) for: {}, result: {}", object, name);
        return name;
    }

    /**
     * 获取方法的全限定名称
     *
     * @param method Method 对象
     * @return 方法的全限定名称
     */
    @NonNull
    public static String getMethodName(@NonNull Method method) {
        var name = ReflectUtils.getClassName(method.getDeclaringClass()) + "." + method.getName();
        log.trace("ReflectUtils.getMethodName(Method) for: {}, result: {}", method, name);
        return name;
    }

    /**
     * 含有泛型参数属性的 Bean 拷贝
     * 1. 源宿属性不要求完全一致，会被忽略或置默认
     * 2. 源中的 Collection 属性会同时被拷贝（按照名称、类型自动转换，互引用会导致无限递归）
     * 3. 宿对象 Collection 属性含有泛型参数时必须提供 TypeReference 引用其泛型参数类型，否则会导致异常
     *
     * @param source        数据源对象
     * @param target        数据宿对象
     * @param typeReference 数据宿对象中泛型参数类型捕获，当不存在泛型参数时可为空，存在泛型参数但未提供时抛出异常
     */
    public static void copy(@NonNull Object source, @NonNull Object target, @Nullable TypeReference<?> typeReference) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            handleCollectionProp(source, target, typeReference);
            log.trace("ReflectUtils.copy(Object, Object, TypeReference<?>) for {}, {}, {}",
                    source, target, typeReference);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            log.warn("Bean copy occur {}, message {}", getClassName(exception), exception.getMessage());
            throw new UtilException(exception);
        }
    }

    /**
     * Bean 属性拷贝
     * 1. 源宿属性不要求完全一致，会被忽略或置默认
     * 2. 源中的 Collection 属性会同时被拷贝（按照名称、类型自动转换，互引用会导致无限递归）
     * 3. 源中数据不得含有泛型参数
     *
     * @param source 数据源对象
     * @param target 数据宿对象
     */
    public static void copy(@NonNull Object source, @NonNull Object target) {
        copy(source, target, null);
        log.trace("ReflectUtils.copy(Object, Object, String...) for {}, {}",
                source, target);
    }

    /**
     * Bean 属性拷贝，返回一个新的宿类型的对象
     * 1. 目标数据对象必须为顶层类
     * 2. 目标数据对象必须由默认无参构造函数
     * 3. 源宿属性不要求完全一致，会被忽略或置默认
     * 4. 源中的 Collection 属性会同时被拷贝（按照名称、类型自动转换，互引用会导致无限递归）
     * 5. 源中数据不得含有泛型参数
     *
     * @param source      数据源对象
     * @param targetClass 目标数据 Class 类型
     * @param <T>         目标数据 Class 类型泛型
     * @return 目标数据对象
     */
    @NonNull
    public static <T> T copy(@NonNull Object source, @NonNull Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            copy(source, target);
            log.trace("ReflectUtils.copy(Object, Class<T>, String...) for {}, {}, result {}",
                    source, targetClass, target);
            return target;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException exception) {
            log.warn("Bean copy occur {}, message {}", getClassName(exception), exception.getMessage());
            throw new UtilException(exception);
        }
    }

    /**
     * Collection 类型拷贝，返回一个新 Collection
     *
     * @param source        数据源 Collection
     * @param typeReference 数据宿对象 TypeReference
     * @param <T>           数据宿对象实际泛型类型
     * @return 数据宿对象 Collection
     */
    public static <T extends Collection<?>> T copy(@NonNull Collection<?> source,
                                                   @NonNull TypeReference<T> typeReference) {
        // Class<?> List<BalaBala>
        var referenceType = (ParameterizedType)
                (((ParameterizedType) typeReference.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        // Class<?> BalaBala
        var targetElementType = (Class<?>) (referenceType.getActualTypeArguments()[0]);
        Collection<Object> target;
        if (List.class.isAssignableFrom(source.getClass())) {
            target = new ArrayList<>();
        } else {
            target = new HashSet<>();
        }
        for (var sourceElement : source) {
            target.add(copy(sourceElement, targetElementType));
        }
        // 没办法了，为了其他地方直接能用，只能强转了
        // 但是这里是一定不会报 ClassCastException，创建了 T 的引用类型，只是原始类型用的 Object
        //noinspection unchecked
        return (T) target;
    }

    /**
     * 处理 Collection 属性
     * 要求元素中名称完全一致，且均为 Collection
     *
     * @param source        数据源对象
     * @param target        数据宿对象
     * @param typeReference 数据宿对象中泛型参数类型捕获，当不存在泛型参数时可为空，存在泛型参数但未提供时抛出异常
     * @param <S>           源数据泛型类
     * @param <T>           宿数据泛型类
     * @throws InvocationTargetException 被调用对象存在异常时抛出
     * @throws IllegalAccessException    被调用对象存在异常时抛出
     */
    private static <S, T> void handleCollectionProp(@NonNull S source, @NonNull T target,
                                                    @Nullable TypeReference<?> typeReference)
            throws InvocationTargetException, IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        var sourcePds = getPropertyDescriptors(sourceClass);
        var targetPds = getPropertyDescriptors(targetClass);
        for (var sourcePd : sourcePds) {
            // 获取源 Getter 判断是否有不在 ignoreProps 中的 Collection 类型并为非 null 值
            if (Collection.class.isAssignableFrom(sourcePd.getPropertyType())
                    && sourcePd.getReadMethod().invoke(source) != null) {
                for (var targetPd : targetPds) {
                    // 源有 Collection，判断宿属性以及类型，不再判断宿该字段是否还未赋值
                    if (targetPd.getName().equals(sourcePd.getName())
                            && Collection.class.isAssignableFrom(targetPd.getPropertyType())) {
                        copyCollectionProp(source, target, sourcePd, targetPd, typeReference);
                    }
                }
            }
        }
    }

    /**
     * 拷贝 Collection 属性
     *
     * @param source        数据源对象
     * @param target        数据宿对象
     * @param sourcePd      数据源 PropertyDescriptor
     * @param targetPd      数据宿 PropertyDescriptor
     * @param typeReference 数据宿对象中泛型参数类型捕获，当不存在泛型参数时可为空，存在泛型参数但未提供时抛出异常
     * @param <S>           源数据泛型类
     * @param <T>           宿数据泛型类
     * @throws InvocationTargetException 被调用对象存在异常时抛出
     * @throws IllegalAccessException    被调用对象存在异常时抛出
     */
    private static <S, T> void copyCollectionProp(@NonNull S source, @NonNull T target,
                                                  @NonNull PropertyDescriptor sourcePd,
                                                  @NonNull PropertyDescriptor targetPd,
                                                  @Nullable TypeReference<?> typeReference)
            throws InvocationTargetException, IllegalAccessException {
        // 只能 <Object> 了，但是不影响
        Collection<Object> targetCollection;
        Class<?> targetCollectionClass = targetPd.getPropertyType();
        if (targetCollectionClass.isAssignableFrom(List.class)) {
            targetCollection = new ArrayList<>();
        } else if (targetCollectionClass.isAssignableFrom(Set.class)) {
            targetCollection = new HashSet<>();
        } else {
            log.error("Unknown Collection type when copy: {}, property: {}",
                    getClassName(source), sourcePd.getName());
            throw new UtilException("Unknown Collection type");
        }
        // 设置引用
        targetPd.getWriteMethod().invoke(target, targetCollection);
        Collection<?> sourceCollection = (Collection<?>) sourcePd.getReadMethod().invoke(source);
        for (var sourceCollectionElement : sourceCollection) {
            try {
                copyCollectionElement(target, targetPd, sourceCollectionElement, targetCollection, typeReference);
            } catch (NoSuchFieldException | NoSuchMethodException exception) {
                log.error("Error when copy bean Collection<?> property, source type: {}, " +
                                "target type: {}, field: {}, exception: {}",
                        getClassName(source), getClassName(target), sourcePd.getName(), exception.getMessage());
                throw new UtilException(exception);
            }
        }
    }

    /**
     * 拷贝 Collection 属性中的元素
     *
     * @param target                  数据宿对象
     * @param targetPd                数据宿 PropertyDescriptor
     * @param sourceCollectionElement 数据源对象中要拷贝的元素
     * @param targetCollection        数据宿对象中 Collection 的新引用
     * @param typeReference           数据宿对象中泛型参数类型捕获，当不存在泛型参数时可为空，存在泛型参数但未提供时抛出异常
     * @param <T>                     宿数据泛型类
     * @throws NoSuchFieldException  数据宿对象无该域
     * @throws NoSuchMethodException 数据宿对象无 genericRealType 方法
     */
    private static <T> void copyCollectionElement(@NonNull T target,
                                                  @NonNull PropertyDescriptor targetPd,
                                                  @NonNull Object sourceCollectionElement,
                                                  @NonNull Collection<Object> targetCollection,
                                                  @Nullable TypeReference<?> typeReference)
            throws NoSuchFieldException, NoSuchMethodException {
        // targetPd 这里的实际类型是 GenericTypeAwarePropertyDescriptor
        // getBeanClass() 方法可以直接得到泛型，但是该类是包可见性，SecurityManager 限制跨不过去
        Class<?> targetCollectionGenericClass;
        var parameterizedType = (((ParameterizedType)
                (target.getClass().getDeclaredField(targetPd.getName()).getGenericType())
        ).getActualTypeArguments()[0]);
        if (parameterizedType instanceof Class<?>) {
            targetCollectionGenericClass = (Class<?>) parameterizedType;
        } else {
            if (typeReference == null) {
                log.error("Copy generic type but no typeReference provide: {}", getClassName(target));
                throw new UtilException("Copy generic type but no typeReference provide: " + getClassName(target));
            }
            targetCollectionGenericClass = (Class<?>)
                    ((ParameterizedType) typeReference.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        // 拷贝出新对象
        var targetCollectionElement = copy(sourceCollectionElement, targetCollectionGenericClass);
        targetCollection.add(targetCollectionElement);
    }
}
