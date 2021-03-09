package javabase.juc;

import lombok.SneakyThrows;

/**
 * @author HaoHao
 * @date 2021/3/8 3:28 下午
 */
public class ThreadTest {

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("t1 持有锁");
                    Thread.sleep(2000);
                    System.out.println("t1 wait");
                    lock.wait();
                    System.out.println("t1 被唤醒");
                }
            }

        });
        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("t2 持有锁");
                    Thread.sleep(4000);
                    lock.notify();
                    System.out.println("t2 notify");
                    Thread.sleep(1000);
                    System.out.println("t2 释放锁");
                }
            }
        });
        // NEW
        System.out.println("t1 state:" + t1.getState());
        t1.start();
        Thread.sleep(10);
        t2.start();
        while (true) {
            System.out.println("t1 state:" + t1.getState());
            Thread.sleep(1000);
        }
    }

}
