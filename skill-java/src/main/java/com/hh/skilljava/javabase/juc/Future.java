package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author HaoHao
 * @date 2021/12/7 5:39 下午
 */
public class Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> supFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @SneakyThrows
            @Override
            public String get() {
                System.out.println("run~");
                return "run return";
            }
        });
        supFuture.get();


        //CompletableFuture<Void> runFutur = CompletableFuture.runAsync(new Runnable() {
        //    @Override
        //    public void run() {
        //
        //    }
        //});
    }
}
