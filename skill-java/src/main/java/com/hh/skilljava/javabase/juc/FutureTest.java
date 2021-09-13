package com.hh.skilljava.javabase.juc;

import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 这个方法返回的 Future 仅可以用来断言任务已经结束了
        // 类似于 Thread.join()。
        Future<?> future = THREAD_POOL_ONE.submit((new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        }));


        // 返回result
        String result = "";
        Future<String> future1 = THREAD_POOL_ONE.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, result);
        String res = future1.get();



        Future<Object> future3 = THREAD_POOL_ONE.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return null;
            }
        });
        String s = future2.get();
    }
}
