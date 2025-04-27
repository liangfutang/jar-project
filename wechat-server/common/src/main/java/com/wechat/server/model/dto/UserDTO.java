package com.wechat.server.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    /**
     * 账号id
     */
    private String userId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 0:直接加入 1:同意加好友
     */
    private Integer joinType;
    /**
     * 0女 1男
     */
    private Integer sex;
    /**
     * 密码
     */
    private String password;
    /**
     * 个性签名
     */
    private String personalSignature;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /**
     * 地区
     */
    private String areaName;
    /**
     * 地区编号
     */
    private String areaCode;
    /**
     * 最后离开时间
     */
    private Long lastOffTime;

    private Integer onlineType;

    private String refreshToken;
    private String accessToken;
}
