package javabase.juc;

import lombok.SneakyThrows;

/**
 * @author HaoHao
 * @date 2021/3/8 3:28 下午
 */
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("1~");
                Thread.sleep(1000);
                System.out.println("2~");
            }
        });
        thread.start();
        // thread加入到当前线程中,顺序执行
        thread.join();
        System.out.println("3~");
    }


    //@Test
    //public void testStart() {
    //    Thread t = new Thread(new Runnable() {
    //        @Override
    //        public void run() {
    //            System.out.println("~");
    //        }
    //    });
    //    t.start();
    //    // IllegalThreadStateException
    //    t.start();
    //}
    //
    //final static ReentrantLock lock = new ReentrantLock();
    //
    ///**
    // * Lock 线程状态
    // */
    //public static void main(String[] args) throws InterruptedException {
    //    Thread t0 = new Thread(new TestStateRunnable(5000));
    //    Thread t1 = new Thread(new TestStateRunnable(0));
    //    Thread printT = new Thread(new PrintStateRunnable(t0, t1));
    //    printT.start();
    //    t0.start();
    //    Thread.sleep(2000);
    //    t1.start();
    //}
    //
    //static class TestStateRunnable implements Runnable {
    //
    //    private long sleep = 0;
    //
    //    public TestStateRunnable(long sleep) {
    //        this.sleep = sleep;
    //    }
    //
    //    @SneakyThrows
    //    @Override
    //    public void run() {
    //        lock.lock();
    //        try {
    //            System.out.println(Thread.currentThread().getName() + "持有锁~");
    //            Thread.sleep(sleep);
    //        } finally {
    //            lock.unlock();
    //            System.out.println(Thread.currentThread().getName() + "释放锁~");
    //        }
    //    }
    //}
    //
    //static class PrintStateRunnable implements Runnable {
    //
    //    Thread[] threads;
    //
    //    public PrintStateRunnable(Thread... threads) {
    //        this.threads = threads;
    //    }
    //
    //    @SneakyThrows
    //    @Override
    //    public void run() {
    //        while (true) {
    //            for (Thread thread : threads) {
    //                System.out.println(thread.getName() + ":" + thread.getState());
    //            }
    //            Thread.sleep(500);
    //        }
    //    }
    //}




    //static Object lock = new Object();
    //
    ///**
    // * Synchronized 线程状态
    // */
    //public static void main(String[] args) throws InterruptedException {
    //
    //
    //    Thread t1 = new Thread(new Runnable() {
    //        @SneakyThrows
    //        @Override
    //        public void run() {
    //            synchronized (lock) {
    //                System.out.println("t1 持有锁");
    //                Thread.sleep(2000);
    //                System.out.println("t1 wait");
    //                lock.wait();
    //                System.out.println("t1 被唤醒");
    //            }
    //        }
    //
    //    });
    //    Thread t2 = new Thread(new Runnable() {
    //        @SneakyThrows
    //        @Override
    //        public void run() {
    //            synchronized (lock) {
    //                System.out.println("t2 持有锁");
    //                Thread.sleep(4000);
    //                lock.notify();
    //                System.out.println("t2 notify");
    //                Thread.sleep(1000);
    //                System.out.println("t2 释放锁");
    //            }
    //        }
    //    });
    //    // NEW
    //    System.out.println("t1 state:" + t1.getState());
    //    t1.start();
    //    Thread.sleep(10);
    //    t2.start();
    //    while (true) {
    //        System.out.println("t1 state:" + t1.getState());
    //        Thread.sleep(1000);
    //    }
    //}

}
