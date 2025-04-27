package com.projects.common.utils;

import com.projects.common.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdUtil.class);

    public TraceIdUtil() {
    }

    public static String getTraceId() {
        return MDC.get(Constant.TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(Constant.TRACE_ID, traceId);
    }

    /**
     * 生成traceId并设置到MDC
     * @param prefix
     * @return traceId
     */
    public static String generateTraceId(String prefix) {
        String traceId = generateTraceIdNo(prefix);
        // 保存MDC
        setTraceId(traceId);
        return traceId;
    }

    /**
     * 生成traceId号
     * @param prefix 生成traceId的前缀
     * @return traceId
     */
    public static String generateTraceIdNo(String prefix) {
        prefix = prefix==null ? "" : prefix + ":";
        String traceId = "";
        try {
            traceId = prefix + UUID.randomUUID().toString().replaceAll("-", "");
        } catch (Exception var2) {
            logger.error("生成traceId失败", var2);
        }
        return traceId;
    }

    public static void removeTraceId() {
        MDC.clear();
    }
}
