package com.hh.skilljava.javabase.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author HaoHao
 * @date 2022/2/7 3:31 下午
 */
public class MyLock {

    private final MyAQS aqs = new MyAQS();

    public void lock(){
        aqs.acquire(1);
    }

    public void unlock(){
        aqs.release(1);
    }

    private static class MyAQS extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
            // 自定义获取锁的规则
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 自定义释放锁规则
            if (getState() == 1) {
                setState(0);
                setExclusiveOwnerThread(null);
                return true;
            }
            return false;
        }
    }
}
