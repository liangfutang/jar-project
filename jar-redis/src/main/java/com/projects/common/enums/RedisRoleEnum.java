package com.projects.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * redis服务中各节点的角色
 */
public enum RedisRoleEnum {

    NONE("none", "无角色关系"),
    MASTER("master", "主节点"),
    SLAVE("slave", "从节点"),
    ;
    @Getter
    private String role;
    @Getter
    private String name;

    RedisRoleEnum(String role, String name) {
        this.role = role;
        this.name = name;
    }

    /**
     * 校验是否是master
     * @param role 角色
     * @return true:是  fase:不是
     */
    public static boolean isMaster(String role) {
        return StringUtils.equals(role, MASTER.getRole());
    }

    /**
     * 校验是否是slave
     * @param role 角色
     * @return true:是  fase:不是
     */
    public static boolean isSlave(String role) {
        return StringUtils.equals(role, SLAVE.getRole());
    }

}
