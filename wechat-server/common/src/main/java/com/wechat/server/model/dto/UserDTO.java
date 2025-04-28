package com.wechat.server.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    /**
     * '姓名'
     */
    private String name;
    /**
     * '密码'
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * '账号状态，0:正常，1:冻结'
     */
    private Integer states;
    /**
     * '操作人'
     */
    private String operator;
    /**
     * '创建时间'
     */
    private String createTime;
    /**
     * '更新时间'
     */
    private String updateTime;
    /**
     * accessToken
     */
    private String accessToken;

    /**
     * refreshToken
     */
    private String refreshToken;
}
