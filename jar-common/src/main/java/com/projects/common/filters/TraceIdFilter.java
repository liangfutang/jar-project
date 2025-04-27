package com.projects.common.filters;

import cn.hutool.core.util.StrUtil;
import com.projects.common.constant.Constant;
import com.projects.common.utils.TraceIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用traceID串联整个请求链路
 * @author liangfu.tang
 */
public class TraceIdFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

    private final String appName;
    public TraceIdFilter(String appName) {
        this.appName = appName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(Constant.TRACE_ID);
            if (StrUtil.isBlank(traceId)) {
                TraceIdUtil.generateTraceId(appName);
            } else {
                TraceIdUtil.setTraceId(traceId);
            }
            logger.info(request.getRequestURI());
        } catch (Exception e) {
            logger.error("生成traceID异常:{}", e.getLocalizedMessage());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            try {
                TraceIdUtil.removeTraceId();
            } catch (Exception e) {
                logger.error("移除traceID异常:", e);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("actuator");
    }
}
