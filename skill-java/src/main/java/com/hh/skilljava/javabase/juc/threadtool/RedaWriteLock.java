package com.hh.skilljava.javabase.juc.threadtool;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author HaoHao
 * @date 2021/3/5 2:51 下午
 */
public class RedaWriteLock {

    private static final Map<String, String> m = new TreeMap<>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();


    public static void main(String[] args) throws InterruptedException {
        // 支持锁降级(即统一线程可以先持有写锁 再获取读锁),不支持锁升级
        w.lock();
        w.unlock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                r.lock();
                r.unlock();
            }
        }).start();
    }
}
