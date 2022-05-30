package com.zzq.multithreading.ctrl;

import com.zzq.multithreading.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AsyncController {
    private final AsyncService asyncService;

    @GetMapping("/async")
    public String async() {
        asyncService.executeAsync();
        return Thread.currentThread().getName();
    }

    @GetMapping("/completableFuture")
    public String completableFuture() {
        asyncService.completableFuture();
        return Thread.currentThread().getName();
    }
}
