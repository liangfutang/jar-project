package com.wechat.server.auth.impl;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.jwt.JWT;
import com.projects.common.constant.Constant;
import com.projects.common.exception.LoginException;
import com.projects.common.exception.NoLoginException;
import com.projects.common.exception.ServiceException;
import com.projects.common.facade.RedisFacade;
import com.projects.common.utils.ParamValidateUtil;
import com.wechat.server.auth.TokenService;
import com.wechat.server.contents.AccountContent;
import com.wechat.server.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j(topic = "TokenServiceImpl")
public class TokenServiceImpl implements TokenService {
    @Value("${sa-token.jwt-secret-key}")
    private String secretKey;
    @Resource
    private RedisFacade redisFacade;

    @Override
    public String refreshToken(Integer userId) {
        long effTime = StpUtil.getTokenTimeout();
        effTime = effTime != -1 ? (StpUtil.getTokenTimeout() + AccountContent.REFRESH_MORE_TIME + System.currentTimeMillis()) : Long.MAX_VALUE;
        JWT jwt = JWT.create()
                .setPayload(Constant.USER_ID, userId)
                .setPayload(Constant.EXPIRES_TIME, effTime)
                .setPayload(Constant.RN_STR, SaFoxUtil.getRandomString(32));
        return SaJwtUtil.generateToken(jwt, secretKey);
    }

    @Override
    public UserDTO tokenVerify(String token, Integer appId) {
        ParamValidateUtil.notNull(appId, "站点id不能为空");

        JWT jwt = this.parseToken(token);
        // 校验token过期
        Long expDate = jwt.getPayload().getClaimsJson().getLong(Constant.EXPIRES_TIME);
        if (expDate == null || new Date().after(new Date(expDate))) {
            log.info("登录过期");
            throw new LoginException("登录过期");
        }

        UserDTO user = new UserDTO();
        user.setId(jwt.getPayload().getClaimsJson().getInt(Constant.USER_ID));
        user.setNickName(jwt.getPayload().getClaimsJson().getStr(Constant.USER_NAME));

        String redisToken = redisFacade.get(String.format(AccountContent.REDIS_TOKEN_KEY, user.getId(), appId));
        if (StringUtils.isBlank(redisToken)) {
            log.info("无效登录,{}", redisToken);
            throw new LoginException("登录无效");
        }
        return user;
    }

    private JWT parseToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new NoLoginException();
        }
        try {
            return SaJwtUtil.parseToken(token, StpUtil.getLoginType(), secretKey, false);
        } catch (Exception e) {
            throw new ServiceException("解析token异常");
        }
    }
}
