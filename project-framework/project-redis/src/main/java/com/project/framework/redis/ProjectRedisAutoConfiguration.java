package com.project.framework.redis;

import com.project.framework.redis.processor.RedissonRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RedissonRegistryPostProcessor.class})
public class ProjectRedisAutoConfiguration {

}
