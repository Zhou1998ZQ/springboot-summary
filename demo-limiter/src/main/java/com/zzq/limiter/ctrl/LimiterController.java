package com.zzq.limiter.ctrl;

import com.zzq.limiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimiterController {


    @GetMapping
    @RateLimiter(time = 1,count = 1)
    public String test(){
        return "hello";
    }
}
