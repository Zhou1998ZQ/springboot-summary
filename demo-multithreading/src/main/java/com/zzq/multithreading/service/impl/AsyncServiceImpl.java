package com.zzq.multithreading.service.impl;

import com.zzq.multithreading.call.AsyncServiceCall;
import com.zzq.multithreading.run.AsyncServiceRun;
import com.zzq.multithreading.service.AsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncServiceImpl implements AsyncService {
    private final ExecutorService executorService;


    @Override
    public void executeAsync() {

        executorService.execute(new AsyncServiceRun());

        Future<?> submit = executorService.submit(new AsyncServiceCall());
        try {
            Object name = submit.get();
            log.info("call -------> {}", name);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void completableFuture() {
        try {
            // same as executeAsync.submit() , CompletableFuture.runAsync no return value
            CompletableFuture<Double> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("completableFuture -------> {}", Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 1d;
            }, executorService);

            // when completableFuture is done, then call thenApplyAsync
            CompletableFuture<Double> doubleCompletableFuture0 = completableFuture.thenApplyAsync(num -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("doubleCompletableFuture0 -------> {}", Thread.currentThread().getName());
                        return num * 2;
                    }
                    , executorService);

            // when completableFuture is done, then call thenApplyAsync
            CompletableFuture<Double> doubleCompletableFuture1 = completableFuture.thenApplyAsync(num -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("doubleCompletableFuture1 -------> {}", Thread.currentThread().getName());
                return num * 3;
            }, executorService);

            // when completableFuture is done, then call  runAsync
            CompletableFuture.runAsync(() -> {
                System.out.println("runAsync -------> " + Thread.currentThread().getName());
            }, executorService);


            CompletableFuture.allOf(doubleCompletableFuture0, doubleCompletableFuture1).get();
            Double d1 = doubleCompletableFuture0.get();
            Double d2 = doubleCompletableFuture1.get();
            log.info("aDouble -------> {}", d1 + d2);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}



