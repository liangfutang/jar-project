package com.projects.common.properties;

import lombok.Data;

@Data
public class RedisProperties {

    private Sentinel sentinel;
    private MasterSlave masterSlave;

}
