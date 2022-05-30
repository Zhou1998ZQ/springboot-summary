package com.zzq.redis.ctrl;

import com.zzq.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    /**
     * By redisTemplate
     * @param redisKey
     * @return
     */
    @GetMapping("/redis/template")
    public String redisTemplate(@RequestParam String redisKey) {
        return redisService.saveOneDay(redisKey);
    }

    /**
     * By Annotation @Cacheable
     */
    @GetMapping("/cache/annotation")
    public String cacheAnnotation(@RequestParam String redisKey) {
        return redisService.saveOneWeek(redisKey);
    }
}
