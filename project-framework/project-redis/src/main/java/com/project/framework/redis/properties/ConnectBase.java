package com.project.framework.redis.properties;

import lombok.Data;

/**
 * 连接基础信息
 */
@Data
public abstract class ConnectBase {
    private String host;
    private Integer port;
    private String password;

    /**
     * 连接池最大连接数
     * 不传，默认64
     */
    private Integer maxPoolSize;
    /**
     * 连接池最小空闲连接数
     * 不传，默认24
     */
    private Integer minimumIdleSize;
}
