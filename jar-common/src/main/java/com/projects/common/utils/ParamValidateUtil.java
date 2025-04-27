package com.projects.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.projects.common.code.ResultCodeEnum;
import com.projects.common.code.ServiceCode;
import com.projects.common.exception.ParameterException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author tlf
 */
public class ParamValidateUtil {

    public static void notNull(Object param, Integer code, String message) {
        Optional.ofNullable(param)
                .orElseThrow(() -> new ParameterException(code, message));
    }

    public static void notNull(Object param, ServiceCode serviceCode) {
        Optional.ofNullable(param)
                .orElseThrow(() -> new ParameterException(serviceCode.getCode(), serviceCode.getMessage()));
    }

    /**
     * 校验参数是否为空
     * @param param 待校验的参数
     * @param message 提示信息，如不传则显示默认提示
     */
    public static void notNull(Object param, String message) {
        Optional.ofNullable(param)
                .orElseThrow(() -> new ParameterException(message));
    }

    /**
     * 校验参数是否为空
     * @param param 被校验参数
     */
    public static void notNull(Object param) {
        Optional.ofNullable(param).orElseThrow(ParameterException::new);
    }

    /**
     * 校验参数中属性是否为空
     * @param t 被校验参数
     * @param validFiled 参数属性
     * @param message 为空时异常信息
     * @param <T, E> 参数类型
     */
    public static <T, E> void notNull(T t, Function<T, E> validFiled, String message) {
        if (t==null || Objects.isNull(validFiled.apply(t))) {
            throw new ParameterException(message);
        }
    }

    /**
     * 校验列表中对象参数中属性是否为空
     * @param list 被校验参数列表
     * @param validFiled 参数属性
     * @param message 为空时异常信息
     * @param <T> 参数列表中对象类型
     * @param <E> 参数对象属性类型
     */
    public static <T, E> void notNull(List<T> list, Function<T, E> validFiled, String message) {
        notEmpty(list, ResultCodeEnum.NO_INPUT_PARAMETER_EXCEPTION.getMessage());
        list.forEach(t -> Optional.ofNullable(validFiled.apply(t)).orElseThrow(() -> new ParameterException(message)));
    }

    public static void notBlank(String param) {
        if (StringUtils.isBlank(param)) {
            throw new ParameterException();
        }
    }

    public static void notBlank(String param, String message) {
        if (StringUtils.isBlank(param)) {
            throw new ParameterException(message);
        }
    }

    public static void notBlank(String param, ServiceCode serviceCode) {
        if (StringUtils.isBlank(param)) {
            throw new ParameterException(serviceCode.getCode(), serviceCode.getMessage());
        }
    }

    public static void notBlank(String param, Integer code, String message) {
        if (StringUtils.isBlank(param)) {
            throw new ParameterException(code, message);
        }
    }

    public static <T> void notBlank(T t, Function<T, String> validFiled) {
        if (t==null || StringUtils.isBlank(validFiled.apply(t))) {
            throw new ParameterException("参数不能为空");
        }
    }

    public static <T> void notBlank(T t, Function<T, String> validFiled, String message) {
        if (t==null || StringUtils.isBlank(validFiled.apply(t))) {
            throw new ParameterException(message);
        }
    }

    /**
     * 校验列表中对象参数中属性是否为空
     * @param list 被校验参数列表
     * @param validFiled 参数属性
     * @param message 为空时异常信息
     * @param <T> 参数类型
     */
    public static <T> void notBlank(List<T> list, Function<T, String> validFiled, String message) {
        notEmpty(list, ResultCodeEnum.NO_INPUT_PARAMETER_EXCEPTION.getMessage());
        list.forEach(t -> {
            if (StringUtils.isBlank(validFiled.apply(t))) {
                throw new ParameterException(message);
            }
        });
    }

    public static <T> void notEmpty(List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            throw new ParameterException("列表不能为空");
        }
    }

    public static <T> void notEmpty(List<T> list, String message) {
        if (CollectionUtil.isEmpty(list)) {
            throw new ParameterException(message);
        }
    }

    /**
     * 判断对象种属性集合是否为空
     * 如：ParamValidateUtil.notEmpty(reservoirMeta, ReservoirMetaDTO::getDamCoorMarkList, "xxx");
     *
     * @param e 需要检查的对象
     * @param list 需要检查的对象中的集合
     * @param message 集合为空时返回的提示信息，为空:默认的信息
     * @param <E> 检查的对象类型
     * @param <T> 对象中集合的类型
     */
    public static <E, T> void notEmpty(E e, Function<E, Collection<T>> list, String message) {
        Optional.ofNullable(e).orElseThrow(ParameterException::new);

        if (list == null) {
            return;
        }

        if (CollectionUtil.isEmpty(list.apply(e))) {
            throw StringUtils.isBlank(message) ? new ParameterException() : new ParameterException(message);
        }
    }

    /**
     * 对象非空，对象中集合列表非空，集合中所有对象逻辑判断
     * @param e 最外层对象
     * @param list 内部的列表参数
     * @param predicate 对列表中每个字段判断逻辑
     * @param message 错误提示
     * @param <E> 入参类型
     * @param <T> 属性列表类型
     */
    public static <E, T> void listFiledNotBlank(E e, Function<E, Collection<T>> list, Predicate<T> predicate, String message) {
        notEmpty(e, list, ResultCodeEnum.PARAMETER_ILLEGAL.getMessage());

        boolean anyMatch = list.apply(e).stream().anyMatch(predicate);
        if (anyMatch) {
            throw StringUtils.isBlank(message) ? new ParameterException() : new ParameterException(message);
        }
    }


    /**
     * 对象非空，对象中集合列表如果非空，则列表中每个对象指定属性非空
     * @param e 最外层对象
     * @param listFunction 内部的列表参数
     * @param listFiledFunction 对列表中每个字段判断逻辑
     * @param message 错误提示
     * @param <E> 入参类型
     * @param <T> 属性列表类型
     * @param <F> 属性列表中对象的属性
     */
    public static <E, T, F> void listFiledNotNullIfListExist(E e, Function<E, Collection<T>> listFunction, Function<T, F> listFiledFunction, String message) {
        notNull(e, ResultCodeEnum.NO_INPUT_PARAMETER_EXCEPTION.getMessage());

        Collection<T> filedList;
        if (listFunction == null || CollectionUtil.isEmpty(filedList = listFunction.apply(e))) {
            return;
        }

        // 判断属性集合中指定属性是否有为空的
        boolean anyMatch = filedList.stream().anyMatch(one -> {
            F f = listFiledFunction.apply(one);
            if (f instanceof String) {
                return StringUtils.isBlank((String) f);
            }
            return Objects.isNull(f);
        });

        if (anyMatch) {
            throw StringUtils.isBlank(message) ? new ParameterException() : new ParameterException(message);
        }
    }

}
