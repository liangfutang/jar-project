package com.project.framework.common.constant;

/**
 * 常用的一些常量值
 * @author tlf
 */
public interface Constant {
    /**
     * B端或者C端传进来的traceID
     */
    String HEADER_X_TRACEID = "X-BOC-TRACEID";
    /**
     * 请求token
     */
    String TOKEN ="Authorization";
    /**
     * 系统内部的请求链路
     */
    String TRACE_ID = "traceId";
    /**
     * 用户id
     */
    String USER_ID = "user-id";
    /**
     * 用户姓名
     */
    String USER_NAME = "user-name";
    /**
     * 手机号
     */
    String PHONE_NO = "phone-no";
    /**
     * 请求终端id
     */
    String APP_ID = "app-id";
    /**
     * 过期时间
     */
    String EXPIRES_TIME = "eff";
    /**
     * key：乱数(混入随机字符串，防止每次生成的token都是一样的)
     */
    String RN_STR = "rnStr";
    /**
     * 租户id
     */
    String TENANT_ID = "tenant-id";
    /**
     * system字段
     */
    String SYSTEM = "system";
    /**
     * 空字符串
     */
    String BLANK = "";

    String SLASH = "/";

    String POINT =".";

    String LINE_BREAK = "\n";

    /**
     * 时间格式 yyyy-MM-dd
     */
    String YY_MM_DD = "yyyy-MM-dd";

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss
     */
    String HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    String HHMMSS = "yyyyMMddHHmmss";

    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss.SSS
     */
    String HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    String UTF8 = "UTF-8";
}
