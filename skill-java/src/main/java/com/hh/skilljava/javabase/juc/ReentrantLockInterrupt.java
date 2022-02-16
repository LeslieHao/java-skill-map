package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断锁
 *
 * @author HaoHao
 * @date 2021/3/9 2:58 下午
 */
public class ReentrantLockInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread0 t0 = new Thread0();
        Thread1 t1 = new Thread1();
        t0.start();
        Thread.sleep(100);
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
    }


    public static ReentrantLock lock = new ReentrantLock();

    public static class Thread0 extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(20000);
                //System.out.println("线程" + Thread.currentThread() + "执行完sleep方法");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static class Thread1 extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            // 会去判断线程是否已经被标记为中断,如果已经被标记,则抛出异常
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.currentThread().getName());
                System.out.println("线程" + Thread.currentThread() + "执行完sleep方法");
            } finally {
                lock.unlock();
            }
        }
    }
}
