package com.project.framework.redis.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.project.framework.common.exception.ServiceException;
import com.project.framework.redis.enums.RedisRoleEnum;
import com.project.framework.redis.properties.MasterSlave;
import com.project.framework.redis.properties.RedisProperties;
import com.project.framework.redis.properties.Sentinel;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.ReadMode;
import org.redisson.config.SingleServerConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 连接redis
 */
public class RedisConnectionHandler {

    public static Map<String, RedissonClient> singleRedissonClient(RedisProperties redisProperties) {
        Map<String, RedissonClient> result = new HashMap<>();

        Sentinel.Connect connect = redisProperties.getSentinel().getConnect();

        for (Sentinel.Client client : redisProperties.getSentinel().getClients()) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            SingleServerConfig singleServerConfig = config.useSingleServer()
                    .setAddress("redis://" + connect.getHost() + ":" + connect.getPort())
                    .setPassword(connect.getPassword())
                    .setDatabase(client.getDatabase());

            Optional.ofNullable(connect.getMinimumIdleSize()).ifPresent(singleServerConfig::setConnectionMinimumIdleSize);
            Optional.ofNullable(connect.getMaxPoolSize()).ifPresent(singleServerConfig::setConnectionPoolSize);

            result.put(client.getBeanName(), Redisson.create(config));
        }

        return result;
    }

    public static Map<String, RedissonClient> masterSlaveRedissonClient(RedisProperties redisProperties) {
        Map<String, RedissonClient> result = new HashMap<>();

        List<MasterSlave.Connect> connectList = redisProperties.getMasterSlave().getConnects();

        // 过滤出主从配置
        List<MasterSlave.Connect> masterRedisList = connectList.stream()
                .filter(one -> RedisRoleEnum.MASTER.getRole().equals(one.getRole())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(masterRedisList) || masterRedisList.size() != 1) {
            throw new ServiceException("主从redis缺少从节点");
        }
        List<MasterSlave.Connect> slaveRedisList = connectList.stream()
                .filter(one -> !RedisRoleEnum.MASTER.getRole().equals(one.getRole())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(slaveRedisList)) {
            throw new ServiceException("主从redis缺少从节点");
        }
        MasterSlave.Connect masterConnect = masterRedisList.get(0);

        // 初始化客户端
        for (MasterSlave.Client client : redisProperties.getMasterSlave().getClients()) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            MasterSlaveServersConfig serversConfig = config.useMasterSlaveServers()
                    .setMasterAddress("redis://" + masterConnect.getHost() + ":" + masterConnect.getPort())
                    .setDatabase(client.getDatabase());

            Optional.ofNullable(masterConnect.getMinimumIdleSize()).ifPresent(serversConfig::setMasterConnectionMinimumIdleSize);
            Optional.ofNullable(masterConnect.getMaxPoolSize()).ifPresent(serversConfig::setMasterConnectionPoolSize);

            for (MasterSlave.Connect slaveConnect : slaveRedisList) {
                serversConfig.addSlaveAddress("redis://" + slaveConnect.getHost() + ":" + slaveConnect.getPort())
                        .setReadMode(ReadMode.SLAVE)
                        .setPassword(slaveConnect.getPassword());

                Optional.ofNullable(slaveConnect.getMinimumIdleSize()).ifPresent(serversConfig::setSlaveConnectionMinimumIdleSize);
                Optional.ofNullable(slaveConnect.getMaxPoolSize()).ifPresent(serversConfig::setSlaveConnectionPoolSize);
            }
            result.put(client.getBeanName(), Redisson.create(config));
        }

        return result;
    }
}
