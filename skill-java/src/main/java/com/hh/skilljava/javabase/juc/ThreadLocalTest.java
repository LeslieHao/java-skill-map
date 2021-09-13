package com.hh.skilljava.javabase.juc;

/**
 * @author HaoHao
 * @date 2021/3/11 11:50 上午
 */
public class ThreadLocalTest {

    static ThreadLocal<Integer> tc = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Integer integer = tc.get();
                    System.out.println(Thread.currentThread() + ":" + integer);
                    tc.set(integer + 1);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
