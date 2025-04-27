package com.wechat.server.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 账号状态枚举值
 */
public enum AccountStateEnum {

    NORMAL(0, "正常"),
    FREEZE(1, "冻结"),
    ;
    /**
     * 状态值
     */
    @Getter
    private final Integer value;
    /**
     * 状态名
     */
    @Getter
    private final String name;
    AccountStateEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    /**
     * 校验状态是否正常
     * @param value 账号状态值
     * @return true:正常， false:不正常
     */
    public static boolean isNormal(Integer value) {
        return Objects.equals(value, NORMAL.getValue());
    }
}
