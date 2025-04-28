package com.project.framework.redis.properties;

import lombok.Data;

@Data
public class RedisProperties {

    private Sentinel sentinel;
    private MasterSlave masterSlave;

}
