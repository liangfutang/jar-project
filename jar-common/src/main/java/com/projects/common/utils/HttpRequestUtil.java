package com.projects.common.utils;

import com.projects.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * http请求的相关工具
 * @author tlf
 */
@Slf4j
public class HttpRequestUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    /**
     * 获取请求中的用户名
     * @return 用户名
     */
    public static String getUserName() {
        return getUserName(null, Constant.SYSTEM, true);
    }

    /**
     * 获取请求中的用户名
     * @param defaultName 没取到使用的默认名
     * @return 用户名
     */
    public static String getUserName(String defaultName) {
        return getUserName(null, defaultName, true);
    }

    /**
     * 获取请求中的用户名
     * @param request 请求, 非必传
     * @return 用户名
     */
    public static String getUserName(HttpServletRequest request) {
        return getUserName(request, Constant.SYSTEM, true);
    }

    /**
     * 获取请求中的用户名
     * @return 用户名
     */
    public static String getUserNameOrigin() {
        return getUserName(null, Constant.SYSTEM, false);
    }

    /**
     * 取出网关塞进来的username
     * @param request 请求, 非必传
     * @param defaultName header中没取到使用的默认名，非必传
     * @param needDecode true:需要，false:不需要
     * @return 网关传递过来的用户名
     */
    public static String getUserName(HttpServletRequest request, String defaultName, boolean needDecode) {
        // 如果没有传进来请求，则自动获取
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return defaultName;
            }
            request = attributes.getRequest();
        }
        String username = request.getHeader(Constant.USER_NAME);
        if (StringUtils.isBlank(username)) {
            return defaultName;
        }
        if (!needDecode) {
            return username;
        }
        // 将拿到的用户名解码
        try {
            username = URLDecoder.decode(username, Constant.UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error("从请求的header中获取username后URLDecoder解析异常:", e);
            return defaultName;
        }
        return username;
    }

    /**
     * 获取token
     * @return token
     */
    public static String getToken() {
        return getToken(null);
    }

    /**
     * 获取token
     * @param request 请求
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        // 如果没有传进来请求，则自动获取
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            request = attributes.getRequest();
        }
        return request.getHeader(Constant.TOKEN);
    }

    /**
     * 取出网关塞进来的 uid
     * @return 用户id
     */
    public static Long getUserId() {
        return getUserId(null);
    }

    /**
     * 取出网关塞进来的 uid
     * @param request 请求
     * @return 用户id
     */
    public static Long getUserId(HttpServletRequest request) {
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            request = attributes.getRequest();
        }
        String uid = request.getHeader(Constant.USER_ID);
        return StringUtils.isBlank(uid) ? null : Long.valueOf(uid);
    }

    /**
     * 取出header中的 appid
     * @return appid
     */
    public static Integer getAppId() {
        return getAppId(null);
    }

    /**
     * 取出header中的 appid
     * @param request 请求
     * @return appid
     */
    public static Integer getAppId(HttpServletRequest request) {
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            request = attributes.getRequest();
        }
        String appId = request.getHeader(Constant.APP_ID);
        return StringUtils.isBlank(appId) ? null : Integer.valueOf(appId);
    }

    /**
     * 取出header中的 tenantId
     * @return tenantId
     */
    public static Integer getTenantId() {
        return getTenantId(null);
    }

    /**
     * 取出header中的 tenantId
     * @param request 请求
     * @return tenantId
     */
    public static Integer getTenantId(HttpServletRequest request) {
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            request = attributes.getRequest();
        }
        String tenantId = request.getHeader(Constant.TENANT_ID);
        return StringUtils.isBlank(tenantId) ? null : Integer.valueOf(tenantId);
    }
}
