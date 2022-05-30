package com.zzq.multithreading.config;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.system.oshi.OshiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;


@Configuration
@Slf4j
public class ExecutorConfig {

    @Bean()
    ExecutorService executorService() {
        int cpuCoreNum = 4;
        try {
            cpuCoreNum = OshiUtil.getCpuInfo().getCpuNum();
        } catch (Throwable e) {
            log.warn("cpu core num error, {}", e.getMessage(), e);
        }

        return ExecutorBuilder.create()
                .setCorePoolSize(cpuCoreNum)
                .setMaxPoolSize(cpuCoreNum * 2 + 1)
                .setWorkQueue(new LinkedBlockingQueue<>(cpuCoreNum * 16))
                .setThreadFactory(new NamedThreadFactory("CE", false))
                .build();
    }
}
