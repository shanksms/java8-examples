package com.shashank.completablefutures;

import com.shashank.completablefutures.model.Email;
import com.shashank.completablefutures.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncExampleMultiBranch {
    public static void main(String[] args) {

        ExecutorService executor1 = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers =
                list -> {
                    Supplier<List<User>> usersSupplier =
                            () -> list.stream().map(User::new).collect(Collectors.toList());

                    return CompletableFuture.supplyAsync(usersSupplier);
                };


        Function<List<Long>, CompletableFuture<List<Email>>> fetchEmails =
                list -> {
            Supplier<List<Email>> emailSupplier = () -> list.stream().map(Email::new).collect(Collectors.toList());
             return CompletableFuture.supplyAsync(emailSupplier);
        };
        CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
        CompletableFuture<List<User>> userFuture = completableFuture.thenCompose(fetchUsers);
        CompletableFuture<List<Email>> emailFuture = completableFuture.thenCompose(fetchEmails);

        userFuture.thenAcceptBoth(emailFuture, (userList, emailList) -> {
            System.out.println("email size " + emailList.size() + " user size " + userList.size());
        });
        executor1.shutdown();
        sleep(1_000);

    }

    private static void  sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
