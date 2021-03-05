package javabase.juc.consumer;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HaoHao
 * @date 2021/3/5 2:56 下午
 */
public class SynchronizeStore {

    private static final AtomicInteger REPERTORY = new AtomicInteger(0);

    public static final int MAX_REPERTORY = 10;

    /**
     * 生产者
     */
    static class Producer extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            for (; ; ) {
                Thread.sleep(50);
                synchronized (REPERTORY) {
                    while (REPERTORY.get() >= MAX_REPERTORY) {
                        // 仓库满了 停止生产
                        System.out.println("仓库已满~ 生产者休息!");
                        REPERTORY.wait();
                    }
                    //增加库存
                    Thread.sleep(100);
                    int i = REPERTORY.incrementAndGet();
                    System.out.println("生产一个商品,当前库存:" + i);
                    // 唤醒等待的线程
                    REPERTORY.notify();
                }
            }
        }
    }

    /**
     * 消费者
     */
    static class Consumer extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            for (; ; ) {
                synchronized (REPERTORY) {
                    while (REPERTORY.get() <= 0) {
                        // 仓库空了
                        System.out.println("仓库已空~ 买东西的等会儿!");
                        REPERTORY.wait();
                    }
                    //增加库存
                    int i = REPERTORY.decrementAndGet();
                    System.out.println("卖出一个商品,当前库存:" + i);
                    Thread.sleep(10);
                    //唤醒等待的线程
                    REPERTORY.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Consumer().start();
        new Producer().start();
    }
}
