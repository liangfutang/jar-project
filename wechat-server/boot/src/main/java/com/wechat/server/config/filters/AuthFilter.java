package com.wechat.server.config.filters;

import com.edu.common.constant.Constant;
import com.edu.common.enums.AppIdEnum;
import com.edu.common.exception.NoLoginException;
import com.edu.common.utils.HttpRequestUtil;
import com.edu.platform.auth.TokenService;
import com.edu.platform.config.filters.wrapper.CustomRequestWrapper;
import com.wechat.server.contents.AccountContent;
import com.wechat.server.model.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserDTO user = null;
        Integer appId = null;
        try {
            String token = HttpRequestUtil.getToken(request);
            if (StringUtils.isBlank(token)) {
                throw new NoLoginException();
            }
            appId = HttpRequestUtil.getAppId(request);
            AppIdEnum.validValue(appId);

            user = tokenService.tokenVerify(token, appId);
            if (user == null) {
                throw new NoLoginException();
            }
        } catch (Exception e) {
            System.out.println("请求异常:" + e.getLocalizedMessage());
            return;
        }

        CustomRequestWrapper headerRequestWrapper = new CustomRequestWrapper(request);
        headerRequestWrapper.addHeader(Constant.USER_ID, user.getId()+"");
        headerRequestWrapper.addHeader(Constant.USER_NAME, user.getName());
        headerRequestWrapper.addHeader(Constant.PHONE_NO, user.getMobile());
        filterChain.doFilter(headerRequestWrapper, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return AccountContent.SKIP_AUTH_URI.contains(request.getRequestURI());
    }
}
