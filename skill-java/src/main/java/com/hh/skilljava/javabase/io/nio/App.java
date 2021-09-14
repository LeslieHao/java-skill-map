package com.hh.skilljava.javabase.io.nio;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author HaoHao
 * @date 2020/5/11 6:36 下午
 */
public class App {

    public final static ThreadPoolExecutor THREAD_POOL
            = new ThreadPoolExecutor(
            10, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(0x3e8),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("bookPoolExecutor" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }, (r, executor) -> System.out.println("reject~"));


    public static void main(String[] args) {
        THREAD_POOL.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Server server = new Server(8000);
                server.listen();
            }
        });

        THREAD_POOL.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Client client = new Client("127.0.0.1", 8000);
                client.listen();
            }
        });
    }
}
