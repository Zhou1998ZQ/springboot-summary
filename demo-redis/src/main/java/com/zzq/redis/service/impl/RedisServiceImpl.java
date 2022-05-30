package com.zzq.redis.service.impl;


import com.zzq.redis.config.CacheManagerConfig;
import com.zzq.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@CacheConfig(cacheManager = CacheManagerConfig.CACHE_LONG)
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_VALUE = "hello-redis";

    @Override
    public String saveOneDay(String redisKey) {
        String value = redisTemplate.opsForValue().get(redisKey);
        if (value == null) {
            redisTemplate.opsForValue().set(redisKey, REDIS_VALUE, 1, TimeUnit.DAYS);
            value = REDIS_VALUE;
        }
        return value;
    }


    /**
     * update new value every day at 10AM
     * Same to use as  @CachePut  @CacheEvict
     * @param redisKey
     * @return
     */
    @Cacheable(cacheNames = "CACHE_NAME", key = "(" +
            "T(cn.hutool.core.date.LocalDateTimeUtil).format(" +
            "T(cn.hutool.core.date.LocalDateTimeUtil).now().minusHours(10),T(cn.hutool.core.date.DatePattern).NORM_DATE_PATTERN)" +
            "+(#redisKey))", unless = "#result == null")
    @Override
    public String saveOneWeek(String redisKey) {
        return REDIS_VALUE;
    }
}
