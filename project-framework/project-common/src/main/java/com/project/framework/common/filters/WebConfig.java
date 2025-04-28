package com.project.framework.common.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * 非网关应用相关的过滤器配置
 *
 * @author tlf
 */
public class WebConfig {

    @Value("${spring.application.name}")
    private String appName;
    /**
     * 设置traceID的过滤器
     *
     * @return 过滤器注册器
     */
    @Bean
    public FilterRegistrationBean<?> traceIdFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceIdFilter(appName));
        registration.addUrlPatterns("/*");
        registration.setName("traceIdFilter");
        // 过滤器顺序
        registration.setOrder(Integer.MIN_VALUE + 100);
        return registration;
    }
}
