package com.hh.skilljava.javabase.juc;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author HaoHao
 * @date 2021/12/5 6:53 下午
 */
@Slf4j
public class SynchronizeTest {

    public static final Object LOCK = new Object();


    public void m1() {
        synchronized (LOCK) {

        }
    }

    public synchronized void m2() {

    }

    /**
     * JDK 15 之后默认关闭偏向锁
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        Object lock = new Object();
        log.info(ClassLayout.parseInstance(lock).toPrintable());
        // 当一个对象已经计算过identity hash code，它就无法进入偏向锁状态
        // 因为mark word里没地方同时放bias信息和identity hash code
        // 调用hashcode 方法后进入同步代码块 会使用轻量级锁
        //lock.hashCode();
        //log.info(ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock){
            // 偏向锁
            log.info(ClassLayout.parseInstance(lock).toPrintable());
            lock.wait(1000);
            // wait 方法是互斥量（重量级锁）独有的，一旦调用该方法，就会升级成重量级锁
            log.info(ClassLayout.parseInstance(lock).toPrintable());
        }

        //synchronized (lock) {
        //    log.info(ClassLayout.parseInstance(lock).toPrintable());
        //}
        //Thread t2 = new Thread(() -> {
        //    synchronized (lock) {
        //        log.info("新线程获取锁，MarkWord为：");
        //        log.info(ClassLayout.parseInstance(lock).toPrintable());
        //    }
        //});
        //t2.start();
        //t2.join();
        //log.info("主线程再次查看锁对象，MarkWord为：");
        //log.info(ClassLayout.parseInstance(lock).toPrintable());

    }
}
