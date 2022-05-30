package com.zzq.redis.service;

public interface RedisService {
    String saveOneDay(String redisKey);

    String saveOneWeek(String redisKey);
}
