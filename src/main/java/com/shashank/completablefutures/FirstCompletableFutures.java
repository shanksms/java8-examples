package com.shashank.completablefutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstCompletableFutures {
    public static void main(String[] args) {
        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> System.out.println("running in thread " + Thread.currentThread().getName());
        CompletableFuture.runAsync(runnable, executorService);
        executorService.shutdown();
    }
}
