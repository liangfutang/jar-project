package com.project.framework.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.project.framework.common.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * fastjson工具类
 * @author tlf
 */
public class FastJsonUtils {

    /**
     * 大括号
     */
    private static final String BRACE = "{}";

    private FastJsonUtils() {
    }

    /**
     * Json 解析帮助类
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        String result = JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.QuoteFieldNames);
        if (result.isEmpty() || BRACE.equals(result)) {
            result = Constant.BLANK;
        }
        return result;
    }

    /**
     * 转换成List
     *
     * @param json json字符串
     * @param type 类型
     * @param <T>  泛型
     * @return List
     */
    public static <T> List<T> toList(String json, Class<T> type) {
        if (StringUtils.isBlank(json) || BRACE.equals(StringUtils.trim(json))) {
            return null;
        }

        return JSONArray.parseArray(json, type);
    }

    /**
     * 转换成Bean
     *
     * @param json json字符串
     * @param type 类型
     * @param <T>  泛型
     * @return Bean
     */
    public static <T> T toBean(String json, Class<T> type) {
        if (StringUtils.isBlank(json) || BRACE.equals(StringUtils.trim(json))) {
            return null;
        }
        return JSON.parseObject(json, type);
    }

    /**
     * 转换成Bean
     *
     * @param json json字符串
     * @param type 类型
     * @param <T>  泛型
     * @return Bean
     */
    public static <T> T toBean(String json, TypeReference<T> type) {
        if (StringUtils.isBlank(json) || BRACE.equals(StringUtils.trim(json))) {
            return null;
        }
        return JSON.parseObject(json, type);
    }

    /**
     * 对象转换
     *
     * @param obj  对象
     * @param type 类型
     * @param <T>  泛型
     * @return T
     */
    public static <T> T convert(Object obj, Class<T> type) {
        String json;
        if (obj instanceof String) {
            json = String.valueOf(obj);
        } else {
            json = toJson(obj);
        }

        return toBean(json, type);
    }
}
