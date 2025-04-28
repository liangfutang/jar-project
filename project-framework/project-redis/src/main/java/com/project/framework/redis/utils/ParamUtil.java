package com.project.framework.redis.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParamUtil {

    /**
     * 校验数组中对象的字段是否重复
     * @param list 需要校验的集合
     * @param function 需要校验的集合中的字段获取函数
     * @param errorMsg 如果重复后报错信息
     * @param <T> 集合对象
     * @param <E> 校验的集合中的字段
     */
    public static <T, E> void checkRepeat(List<T> list, Function<T, E> function, String errorMsg) {
        Map<E, List<T>> map = list.stream().collect(Collectors.groupingBy(function));
        if (map.size() != list.size()) {
            throw new RuntimeException(errorMsg);
        }
    }
}
