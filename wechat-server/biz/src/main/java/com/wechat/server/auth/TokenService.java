package com.wechat.server.auth;

import com.wechat.server.model.dto.UserDTO;

public interface TokenService {
    /**
     * 生成刷新token
     * @param userId 用户id
     * @return 刷新token
     */
    String refreshToken(Integer userId);

    /**
     * 对token有效性进行校验
     * @param token 请求传递的token
     * @param appId 站点id
     * @return 解析出的用户信息
     */
    UserDTO tokenVerify(String token, Integer appId);
}
