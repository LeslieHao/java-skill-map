package com.hh.skilljava.javabase.juc;

/**
 * @author HaoHao
 * @date 2022/2/12 1:23 下午
 */
public class TestCacheLine {
    static class Padding {
        // 缓存填充
        long p1, p2, p3, p4, p5, p6, p7;
    }

    static class Obj extends Padding{
        volatile long x = 0L;
    }

    static Obj[] arr1 = new Obj[]{new Obj(), new Obj()};


    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (long i = 0; i < 10000000L; i++) {
                    arr1[0].x = i;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (long i = 0; i < 10000000L; i++) {
                    arr1[1].x = i;
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - b);
    }

}
