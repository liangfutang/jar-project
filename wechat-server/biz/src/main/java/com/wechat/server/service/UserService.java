package com.wechat.server.service;

import com.wechat.server.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> selectByAccount(String account);

    /**
     * 新增一个用户
     * @param user 新增的用户信息
     * @return 新增结果
     */
    int insert(UserDTO user);
}
