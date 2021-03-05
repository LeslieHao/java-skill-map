package javabase.juc.consumer;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HaoHao
 * @date 2021/3/3 3:48 下午
 */
public class ReentrantLockStore {

    /**
     * 库存
     */
    private static AtomicInteger repertory = new AtomicInteger(0);

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition notFull = lock.newCondition();

    private static Condition notEmpty = lock.newCondition();

    public static final int MAX_REPERTORY = 100;

    /**
     * 生产者
     */
    static class Producer extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            lock.lock();
            try {
                while (repertory.get() >= MAX_REPERTORY) {
                    // 仓库满了 停止生产
                    System.out.println("仓库已满~ 生产者休息!");
                    notFull.await();
                }
                //增加库存
                Thread.sleep(1000);
                int i = repertory.incrementAndGet();
                System.out.println("生产一个商品,当前库存:" + i);
                // 唤醒在等待上货的线程
                notEmpty.signalAll();
            } finally {
                lock.unlock();
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
            lock.lock();
            try {
                while (repertory.get() <= 0) {
                    // 仓库空了
                    System.out.println("仓库已空~ 买东西的等会儿!");
                    notEmpty.await();
                }  //增加库存
                Thread.sleep(1000);
                int i = repertory.decrementAndGet();
                System.out.println("卖出一个商品,当前库存:" + i);
                // 唤醒等待生产的线程
                notFull.signalAll();
            } finally {
                lock.unlock();
            }

        }
    }
}
