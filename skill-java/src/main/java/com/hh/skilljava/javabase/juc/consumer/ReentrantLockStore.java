package com.hh.skilljava.javabase.juc.consumer;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lock 实现生产消费模型
 *
 * @author HaoHao
 * @date 2021/3/3 3:48 下午
 */
public class ReentrantLockStore {

    /**
     * 库存
     */
    private static AtomicInteger repertory = new AtomicInteger(10);

    /**
     * 非公平:会出现生产者一直持有锁直到触发await
     * 公平:则生产者和消费者交替持有锁
     */
    private static ReentrantLock lock = new ReentrantLock(true);

    //private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //private static ReentrantReadWriteLock.ReadLock lock = readWriteLock.readLock();

    private static Condition full = lock.newCondition();

    private static Condition empty = lock.newCondition();

    public static final int MAX_REPERTORY = 2;

    /**
     * 生产者
     */
    static class Producer extends Thread {

        public Producer() {
            super("Producer-Thread");
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (repertory.get() >= MAX_REPERTORY) {
                        // 仓库满了 停止生产
                        System.out.println("仓库已满~ 生产者休息!");
                        full.await();
                    }
                    //增加库存
                    Thread.sleep(1000);
                    int i = repertory.addAndGet(2);
                    System.out.println("生产一个商品,当前库存:" + i);
                    // 唤醒在等待上货的线程
                    empty.signalAll();
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    /**
     * 消费者
     */
    static class Consumer extends Thread {

        public Consumer() {
            super("Consumer-Thread");
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (repertory.get() <= 0) {
                        // 仓库空了
                        System.out.println("仓库已空~ 买东西的等会儿!");
                        empty.await();
                    }  //增加库存
                    Thread.sleep(500);
                    int i = repertory.decrementAndGet();
                    System.out.println("卖出一个商品,当前库存:" + i);
                    // 唤醒等待生产的线程
                    full.signalAll();
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    public static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }
}
