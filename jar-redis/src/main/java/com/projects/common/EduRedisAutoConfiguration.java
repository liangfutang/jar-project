package com.projects.common;

import com.projects.common.processor.RedissonRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RedissonRegistryPostProcessor.class})
public class EduRedisAutoConfiguration {

}
