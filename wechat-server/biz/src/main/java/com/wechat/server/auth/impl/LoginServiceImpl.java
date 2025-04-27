package com.wechat.server.auth.impl;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.projects.common.constant.Constant;
import com.projects.common.exception.LoginException;
import com.projects.common.exception.NoAccountException;
import com.projects.common.facade.RedisFacade;
import com.projects.common.utils.HttpRequestUtil;
import com.wechat.server.auth.LoginService;
import com.wechat.server.auth.PasswordEncoder;
import com.wechat.server.auth.TokenService;
import com.wechat.server.contents.AccountContent;
import com.wechat.server.enums.AccountStateEnum;
import com.wechat.server.model.dto.UserDTO;
import com.wechat.server.model.param.UserApiParam;
import com.wechat.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;
    private final RedisFacade redisFacade;

    @Override
    public UserDTO login(UserApiParam user) {
        // 获取用户信息
        List<UserDTO> users = userService.selectByAccount(user.getAccount());
        if (CollectionUtil.isEmpty(users) || users.size() > 1) {
            throw new NoAccountException("账号不存在或账号异常");
        }
        UserDTO existUser = users.get(0);
        if (!AccountStateEnum.isNormal(existUser.getStatus())) {
            throw new LoginException("账号状态异常，请联系管理员");
        }
        // 密码匹配
        if (!passwordEncoder.matches(user.getPassword(), existUser.getPassword())) {
            throw new LoginException("账号或密码错误");
        }
        // 登录创建token信息
        existUser.setPassword(null);
        StpUtil.login(existUser.getId(), SaLoginConfig
                .setExtra(Constant.USER_NAME, existUser.getNickName())
                .setExtra(Constant.USER_ID, existUser.getId()));
        String accessToken = StpUtil.getTokenInfo().getTokenValue();
        String refreshToken = tokenService.refreshToken(existUser.getId());
        existUser.setRefreshToken(refreshToken);
        existUser.setAccessToken(accessToken);
        // 存储缓存
        redisFacade.set(String.format(AccountContent.REDIS_TOKEN_KEY, existUser.getId(), HttpRequestUtil.getAppId()), accessToken, TimeUnit.SECONDS, StpUtil.getTokenTimeout());
        redisFacade.set(String.format(AccountContent.REDIS_REFRESH_TOKEN_KEY, existUser.getId(), HttpRequestUtil.getAppId()), refreshToken, TimeUnit.SECONDS, StpUtil.getTokenTimeout()+AccountContent.REFRESH_MORE_TIME);
        return existUser;
    }

    @Override
    public int register(UserDTO user) {
        return 0;
    }
}
