package com.hh.skilljava.javabase.juc.threadtool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 *
 * @author HaoHao
 * @date 2021/11/9 8:38 下午
 */
@Slf4j
public class CyclicBarrierTest {

    final static CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            log.info("barrierAction run~");
        }
    });


    static class Worker implements Runnable {

        String name;

        public Worker(String name) {
            this.name = name;
        }

        @SneakyThrows
        @Override
        public void run() {
            log.info(name + " begin~");
            barrier.await();
            log.info(name + " end~");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            new Thread(new Worker("worker" + i)).start();
            Thread.sleep(1000);
        }
    }

}
