package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author HaoHao
 * @date 2021/3/17 5:30 下午
 */
@Slf4j
public class ThreadPoolTest {


    public final static ThreadPoolExecutor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(
            10, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(0x3e8),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("bookPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }, (r, executor1) -> log.error("bookPoolExecutor reject: {}", r.toString()));

    public final static ThreadPoolExecutor THREAD_POOL_ONE
            = new ThreadPoolExecutor(
            5, 10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("bookPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }, (r, executor1) -> log.error("bookPoolExecutor reject: {}", r.toString()));


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            THREAD_POOL_ONE.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("~");
                }
            });
        }

        Thread.sleep(1000);
        Date date = new Date();
        WeakReference<Date> data = new WeakReference(date);

        System.out.println(THREAD_POOL_ONE.getLargestPoolSize());
    }

    /**
     * 借款初始化页线程池
     */
    //public final static Executor LOAN_INIT_THREAD_POOL_EXECUTOR
    //        = new TraceExcutorService(new ThreadPoolExecutor(
    //        10, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(0x3e8),
    //        runnable -> {
    //            Thread thread = new Thread(runnable);
    //            thread.setName("loanInitPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
    //            return thread;
    //        }, (r, executor1) -> log.error("loanInitPoolExecutor reject: {}", r.toString())));

}
