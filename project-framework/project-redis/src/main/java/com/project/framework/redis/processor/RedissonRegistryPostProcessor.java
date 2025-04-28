package com.project.framework.redis.processor;

import cn.hutool.core.collection.CollectionUtil;
import com.project.framework.redis.constants.RedisConstant;
import com.project.framework.redis.facade.RedisFacade;
import com.project.framework.redis.handler.RedisConnectionHandler;
import com.project.framework.redis.properties.RedisProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 连接所有的redis
 */
@Configuration
@Data
@Slf4j
public class RedissonRegistryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware, DisposableBean {
    private RedisProperties redisProperties;
    private Collection<RedissonClient> redissonClients;
    @Resource
    private List<RedisFacade> redisFacades;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        // 初始化客户端
        Map<String, RedissonClient> beanName2client = null;
        if (redisProperties.getSentinel() != null) {
            beanName2client = RedisConnectionHandler.singleRedissonClient(redisProperties);
        } else if (redisProperties.getMasterSlave() != null) {
            beanName2client = RedisConnectionHandler.masterSlaveRedissonClient(redisProperties);
        }
        if (CollectionUtil.isEmpty(beanName2client)) {
            return;
        }
        redissonClients = beanName2client.values();
        // 初测spring容器
        for (Map.Entry<String, RedissonClient> entry : beanName2client.entrySet()) {
            factory.registerSingleton(entry.getKey().replace(RedisConstant.CLIENT_BEAN_NAME, RedisConstant.REDISSON_CLIENT_BEAN_NAME), entry.getValue());
            BeanDefinitionBuilder b = BeanDefinitionBuilder
                    .genericBeanDefinition(RedisFacade.class)
                    .addConstructorArgValue(entry.getValue());
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(entry.getKey(), b.getBeanDefinition());
            // 因为DB0较为常用，所以需要对DB0做特殊处理
            if ((RedisConstant.CLIENT_BEAN_NAME + 0).equals(entry.getKey())) {
                factory.registerAlias(RedisConstant.CLIENT_BEAN_NAME + 0, RedisConstant.CLIENT_BEAN_NAME);
                factory.registerAlias(RedisConstant.REDISSON_CLIENT_BEAN_NAME + 0, RedisConstant.REDISSON_CLIENT_BEAN_NAME);
            }
            if ((RedisConstant.CLIENT_BEAN_NAME).equals(entry.getKey())) {
                factory.registerAlias(RedisConstant.CLIENT_BEAN_NAME, RedisConstant.CLIENT_BEAN_NAME + 0);
                factory.registerAlias(RedisConstant.REDISSON_CLIENT_BEAN_NAME, RedisConstant.REDISSON_CLIENT_BEAN_NAME + 0);
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 绑定redis配置
        redisProperties = Binder.get(environment).bind("project.redis", Bindable.of(RedisProperties.class)).get();
        // 校验配置参数是否符合规范
        this.checkProperties();
    }

    /**
     * 校验redis配置参数
     */
    private void checkProperties() {
        Optional.ofNullable(redisProperties).orElseThrow(() -> new RuntimeException("无redis配置参数"));
        // 校验部署模式，一个模式只能有一种
        AtomicInteger modelCount = new AtomicInteger(0);
        Optional.ofNullable(redisProperties.getSentinel()).ifPresent(sentinel -> modelCount.incrementAndGet());
        Optional.ofNullable(redisProperties.getMasterSlave()).ifPresent(masterSlave -> modelCount.incrementAndGet());
        if (modelCount.get() <= 0) {
            throw new RuntimeException("缺少redis配置参数");
        }
        if (modelCount.get() > 1) {
            throw new RuntimeException("redis配置参数重复");
        }

        // 校验singel模式
        if (redisProperties.getSentinel() != null) {
            // 触发一下校验
            redisProperties.getSentinel().checkAndFillClients();
            redisProperties.getSentinel().checkAndFillConnect();
        }

        // 校验masterSlave模式
        if (redisProperties.getMasterSlave() != null) {
            // 触发一次校验
            redisProperties.getMasterSlave().checkAndFilConnects();
            redisProperties.getMasterSlave().checkAndFillClients();
        }
    }

    @Override
    public void destroy() throws Exception {
        if (redissonClients == null) {
            return;
        }
        for (RedissonClient client : redissonClients) {
            if (!client.isShuttingDown() && !client.isShutdown()) {
                client.shutdown();
                System.out.println("redis 正在注销:" + client);
            }
        }
        System.out.println("redis 注销完成");
    }
}
