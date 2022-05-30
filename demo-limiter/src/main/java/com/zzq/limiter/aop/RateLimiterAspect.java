package com.zzq.limiter.aop;

import com.zzq.limiter.annotation.RateLimiter;
import com.zzq.limiter.exception.LimiterException;
import com.zzq.limiter.util.IpUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final RedisTemplate<Object, Object> redisTemplate;

    private final RedisScript<Long> limitScript;

    @Pointcut("@annotation(com.zzq.limiter.annotation.RateLimiter)")
    public void rateLimiterPointCut() {
    }

    @Before("rateLimiterPointCut()")
    public void doBefore(JoinPoint point) {
        try {
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            RateLimiter rateLimiter = methodSignature.getMethod().getAnnotation(RateLimiter.class);
            String key = rateLimiter.key();
            int time = rateLimiter.time();
            int count = rateLimiter.count();

            String combineKey = getCombineKey(rateLimiter, point);
            List<Object> keys = Collections.singletonList(combineKey);

            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (Objects.isNull(number) || number.intValue() > count) {
                throw new LimiterException(HttpStatus.TOO_MANY_REQUESTS, "too many requests");
            }
            log.info("limiter request'{}',current request'{}',cache key'{}'", count, number.intValue(), key);
        } catch (Exception e) {
            if (e instanceof LimiterException) {
                throw e;
            }
            throw new RuntimeException("server limiter error", e);
        }
    }


    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if ("IP".equals(rateLimiter.limitType())) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                stringBuffer.append(IpUtil.getIpAddr(request));
            }

        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append("-").append(targetClass.getName()).append("- ").append(method.getName());
        return stringBuffer.toString();
    }
}
