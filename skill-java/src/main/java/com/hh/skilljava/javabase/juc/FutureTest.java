package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author HaoHao
 * @date 2021/6/9 3:47 下午
 */

@Slf4j
public class FutureTest {

    public final static ThreadPoolExecutor THREAD_POOL_ONE
            = new ThreadPoolExecutor(
            5, 10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("bookPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }, (r, executor1) -> log.error("bookPoolExecutor reject: {}", r.toString()));


    public static void m1() throws ExecutionException, InterruptedException {
        Future<String> f2 = THREAD_POOL_ONE.submit((new Runnable() {
            @Override
            public void run() {
                System.out.println("do~");
            }
        }), "ok");
        // 返回submit 时传入的result
        String res = f2.get();
        System.out.println(res);
    }

    public static void m2() throws ExecutionException, InterruptedException {
        Future<?> f1 = THREAD_POOL_ONE.submit((new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        }));
        // 返回空
        Object o = f1.get();
    }

    public static void m3() throws ExecutionException, InterruptedException {
        Future<String> future = THREAD_POOL_ONE.submit((new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ok";
            }
        }));
        // 返回子线程返回的
        String res = future.get();
        System.out.println(res);
    }

    public static void m4() throws ExecutionException, InterruptedException {
        // 有返回
        // 底层封装runnable(装饰器模式)
        // run 方法会先执行get方法
        // 然后执行写入结果到future
        // 最后执行 postComplete(这里会唤醒park 在future.get() 上的线程)
        // 等待线程被封装成对象 存入一个链表中


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @SneakyThrows
            @Override
            public String get() {
                Thread.sleep(2);
                return "ok";
            }
        }, THREAD_POOL_ONE);
        String s = future2.get();
        System.out.println(s);
    }

    public static void m5() throws ExecutionException, InterruptedException {
        // 无返回
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {

            }
        });
        future.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync((Supplier<List<String>>) () -> {
            log.info("do get~");
            return new ArrayList<>();
        }).whenCompleteAsync((list, throwable) -> {
            log.info("do whenCompleteAsync");
            list.add("whenCompleteAsync");
        }).whenCompleteAsync((list, throwable) -> {
            log.info("do whenComplete~");
            list.add("whenComplete");
        }).whenCompleteAsync((list, throwable) -> {
            log.info("do whenComplete~");
            list.add("whenComplete");
        }).whenCompleteAsync((list, throwable) -> {
            log.info("do whenComplete~");
            list.add("whenComplete");
        });
        List<String> list = future.get();
        System.out.println("");
    }
}
