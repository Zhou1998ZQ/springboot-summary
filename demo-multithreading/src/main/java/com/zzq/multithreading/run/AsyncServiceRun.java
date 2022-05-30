package com.zzq.multithreading.run;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncServiceRun implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(3000);
        String name = Thread.currentThread().getName();
        log.info("run -------> {}", name);

    }
}
