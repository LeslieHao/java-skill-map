package com.hh.skilljava.javabase.juc.threadtool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author HaoHao
 * @date 2021/11/10 3:53 下午
 */
@Slf4j
public class CountDownLatchTest {

    static CountDownLatch countDownLatch = new CountDownLatch(2);


    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("main run~");
                countDownLatch.await();
                log.info("main end~");
            }
        }, "main").start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("main2 run~");
                countDownLatch.await();
                log.info("main2 end~");
            }
        }, "main2").start();

        for (int i = 0; i < 2; i++) {
            Thread.sleep(1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    log.info("child countDown~");
                }
            }, "child" + i).start();
        }
    }


}
