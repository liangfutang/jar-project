package com.project.framework.redis.properties;

import lombok.Data;

/**
 * 客户端基础信息
 */
@Data
public abstract class ClientBase {
    /**
     * 客户端连接到redis的db
     */
    private Integer database = 0;
    /**
     * 指定连接当前db的客户端beanname，如不指定，则默认使用客户端名加上db号，如：redissonClient0
     */
    private String beanName;
}
