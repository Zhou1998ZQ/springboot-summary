package com.zzq.multithreading.call;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Callable;


public class AsyncServiceCall implements Callable<String> {

    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}
