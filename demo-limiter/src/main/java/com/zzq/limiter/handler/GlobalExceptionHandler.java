package com.zzq.limiter.handler;

import com.zzq.limiter.exception.LimiterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.zzq.limiter")
public class GlobalExceptionHandler {

    @ExceptionHandler(LimiterException.class)
    public String limiterExceptionHandler(LimiterException exception) {
        return exception.getMessage();
    }


    @ExceptionHandler(Exception.class)
    public String exceptionHandle(Exception exception) {
        return exception.getMessage();
    }
}
