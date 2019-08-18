package com.shashank.completablefutures;

import com.shashank.completablefutures.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncExample {
    public static void main(String[] args) {
        Executor executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        Supplier<List<Long>> supplyIDs = () -> {
          sleep(200);
          return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers =
        list -> {
            Supplier<List<User>> usersSupplier =
                    () -> {
                        System.out.println("running in " + Thread.currentThread().getName());
                        return list.stream().map(User::new).collect(Collectors.toList());
                    };

            return CompletableFuture.supplyAsync(usersSupplier, executor2);
        };

        Consumer<List<User>> displayer = users -> {
            System.out.println("Running in " + Thread.currentThread().getName());
            users.forEach(System.out::println);
        };

        CompletableFuture.supplyAsync(supplyIDs)
                .thenCompose(fetchUsers)
                .thenAcceptAsync(displayer, executor1);
        sleep(1_000);
        ((ExecutorService) executor1).shutdown();
        executor2.shutdown();
    }

    private static void  sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
