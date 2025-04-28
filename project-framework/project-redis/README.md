# 项目简介
为后端系统提供公用的模块对接组件

# 接入说明
## project-redis
1. 添加maven依赖
```xml
<dependency>
   <groupId>com.project.framework</groupId>
   <artifactId>project-redis</artifactId>
   <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 添加配置
```yaml
project:
  redis:
    sentinel:
      connect:
        host: 123.60.162.247 # 必传
        port: 6379  # 必传
        password: D$kPM3GO&j5vTN$i # 必传
        maxPoolSize: 64 # 非必传，连接池最大连接数，默认64
        minimumIdleSize: 24 # 非必传，连接池最小空闲连接数，默认24
      clients: # 如果只有一个客户端，则可不传，默认db0
        - database: 0  # 非必传，默认0，如果有多个客户端，则db不重复
          beanName: redissonClient1  # 非必传,无特殊需要则不填，如果有多个客户端，则name不重复
    masterSlave:
      connects:
        - host: 123.60.162.247 # 必传
          port: 6379  # 必传
          password: D$kPM3GO&j5vTN$i # 必传
          maxPoolSize: 64 # 非必传，连接池最大连接数，默认64
          minimumIdleSize: 24 # 非必传，连接池最小空闲连接数，默认24
          role: master # 当前连接的redis服务节点在整个redis服务中扮演的角色信息,RedisRoleEnum
      clients:
        - database: 0  # 必传，不重复
          beanName: redissonClient1  # 非必传，如果有多个客户端，则name不重复
```

3. 使用
   添加注入
```
@Resource(name = "redissonClient5")
private RedissonClient redissonClient;
@Resource
private RedissonClient redissonClient6;
@Resource
private RedissonClient redissonClient;  // 当前注入和下面的注入都表示对DB0的操作
@Resource
private RedissonClient redissonClient0;

// 为方便对redis常用方法操作，也可以通过下面的方式操作
或
@Resource
private RedisFacade redisFacade;
@Resource
private RedisFacade redisFacade0;
@Resource
private RedisFacade redissonClient5;
```
