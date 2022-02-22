package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HaoHao
 * @date 2021/3/17 5:30 下午
 */
@Slf4j
public class ThreadPoolTest {

    public final static ThreadPoolExecutor THREAD_POOL
            = new ThreadPoolExecutor(
            8,
            8,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("MyPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                    return thread;
                }
            });


    public final static ThreadPoolExecutor THREAD_POOL_ONE
            = new ThreadPoolExecutor(
            1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("bookPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }, new ThreadPoolExecutor.DiscardPolicy());

    static final AtomicBoolean flag = new AtomicBoolean(true);

    static final AtomicInteger iii = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        //System.out.println(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 10; i++) {
            THREAD_POOL_ONE.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    log.info(iii.incrementAndGet() + "");
                }
            });
        }

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
