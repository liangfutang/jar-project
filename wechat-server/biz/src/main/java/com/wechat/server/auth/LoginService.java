package com.wechat.server.auth;


import com.wechat.server.model.dto.UserDTO;
import com.wechat.server.model.param.UserApiParam;

public interface LoginService {

    UserDTO login(UserApiParam user);

    /**
     * 注册用户
     * @param user 用户信息
     * @return 注册结果
     */
    int register(UserDTO user);
}
