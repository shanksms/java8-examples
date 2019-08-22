package com.shashank.completablefutures;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.amazon.com"))
                .build();
        CompletableFuture<Void> start = new CompletableFuture<>();

        CompletableFuture<HttpResponse<String>> future
                = start.thenCompose(
                        nil -> httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
        );
        future.thenAcceptAsync(response -> {
            int length = response.body().length();
            System.out.println("length " + length + " in " + Thread.currentThread().getName());
        }).thenRun(() -> System.out.println("Done"));

        start.complete(null);
        Thread.sleep(60000);

    }

}
