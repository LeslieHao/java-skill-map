package com.hh.skilljava.javabase.juc;

/**
 * @author HaoHao
 * @date 2021/12/5 6:53 下午
 */
public class SynchronizeTest {

    public static final Object LOCK = new Object();


    public void m1() {
        synchronized (LOCK) {

        }
    }

    public synchronized void m2() {

    }
}
