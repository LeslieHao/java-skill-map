package com.hh.skilljava.javabase.juc.threadtool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * 计数信号量(底层也是基于AQS)
 *
 * 限制资源的并发访问数
 *
 * @author HaoHao
 * @date 2021/11/8 8:08 下午
 */
@Slf4j
public class SemaphoreTest {

    private static final int MAX_AVAILABLE = 4;

    private static final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    public static void getItem() throws InterruptedException {
        available.acquire();
        log.info("Thread:{} 拿到资源", Thread.currentThread().getName());
        Thread.sleep(10 * 1000);
        log.info("Thread:{} 释放资源", Thread.currentThread().getName());
        available.release();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    getItem();
                }
            }, "T" + i).start();
        }
    }
}
