package com.project.framework.redis.facade;

import com.project.framework.common.code.ResultCodeEnum;
import com.project.framework.common.exception.RedisOperationException;
import com.project.framework.common.utils.FastJsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBatch;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class RedisFacade {
    @Getter
    private final RedissonClient redissonClient;
    public RedisFacade(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public <T> void set(String key, T value, TimeUnit timeUnit, long expire) {
        try {
            redissonClient.getBucket(key).set(FastJsonUtils.toJson(value), expire, timeUnit);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void setAsync(String key, T value, TimeUnit timeUnit, long expire) {
        try {
            redissonClient.getBucket(key).setAsync(FastJsonUtils.toJson(value), expire, timeUnit);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void set(String key, T value) {
        try {
            redissonClient.getBucket(key).set(FastJsonUtils.toJson(value));
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void setAsync(String key, T value) {
        try {
            redissonClient.getBucket(key).setAsync(FastJsonUtils.toJson(value));
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }

    public <T> void set(String key, String value, TimeUnit timeUnit, long expire) {
        try {
            redissonClient.getBucket(key).set(value, expire, timeUnit);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void setAsync(String key, String value, TimeUnit timeUnit, long expire) {
        try {
            redissonClient.getBucket(key).setAsync(value, expire, timeUnit);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void set(String key, String value) {
        try {
            redissonClient.getBucket(key).set(value);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }
    public <T> void setAsync(String key, String value) {
        try {
            redissonClient.getBucket(key).setAsync(value);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_WRITE_ERROR.code(),
                    ResultCodeEnum.REDIS_WRITE_ERROR.message());
        }
    }

    public String get(String key) {
        try {
            Object o = redissonClient.getBucket(key).get();
            return o == null ? null : String.valueOf(o);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_READ_ERROR.code(),
                    ResultCodeEnum.REDIS_READ_ERROR.message());
        }
    }
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object o = redissonClient.getBucket(key).get();
            return o == null ? null : FastJsonUtils.convert(o, clazz);
        } catch (Exception e) {
            throw new RedisOperationException(ResultCodeEnum.REDIS_READ_ERROR.code(),
                    ResultCodeEnum.REDIS_READ_ERROR.message());
        }
    }


    public void batch(Consumer<RBatch> consumer) {
        RBatch batch = redissonClient.createBatch();
        try {
            consumer.accept(batch);
            batch.execute();
        } catch (Throwable t) {
            batch.discard();
            log.error("redis batch error {}", t.toString());
        }
    }

    public void batchAsync(Consumer<RBatch> consumer) {
        RBatch batch = redissonClient.createBatch();
        try {
            consumer.accept(batch);
            batch.executeAsync();
        } catch (Throwable t) {
            batch.discardAsync();
            log.error("redis async batch error {}", t.toString());
        }
    }

}
