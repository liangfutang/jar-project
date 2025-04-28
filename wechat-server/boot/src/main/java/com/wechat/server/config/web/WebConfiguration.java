package com.wechat.server.config.web;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import com.wechat.server.auth.TokenService;
import com.wechat.server.config.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class WebConfiguration {
    @Autowired
    private TokenService tokenService;

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

    @Bean
    public FilterRegistrationBean<?> authFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthFilter(tokenService));
        registration.addUrlPatterns("/*");
        registration.setName("authFilter");
        // 过滤器顺序
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return registration;
    }

    /**
     * 暂时解决本地开发时跨域问题，实际线上跨域由nginx解决
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        corsFilterFilterRegistrationBean.setFilter(new CorsFilter(source));
        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterFilterRegistrationBean;
    }
}
