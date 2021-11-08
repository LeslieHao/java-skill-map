package com.hh.skilljava.javabase.juc.consumer;

import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现生产消费模型
 *
 * @author HaoHao
 * @date 2021/11/8 2:46 下午
 */
public class BlockQueueStore {


    private static final BlockingQueue<Object> REPOSITORY = new ArrayBlockingQueue<>(10);


    static class Producer implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                REPOSITORY.put(new Object());
                System.out.println("producer +1");
                Thread.sleep(1000);
            }
        }

    }


    static class Consumer implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                REPOSITORY.take();
                System.out.println("Consumer -1");
                Thread.sleep(1000);
            }
        }

    }


    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();

    }


}
