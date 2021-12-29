package com.hh.skilljava.javabase.juc;

import java.util.concurrent.SynchronousQueue;

/**
 * @author HaoHao
 * @date 2021/12/22 9:11 下午
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        // put 之后会进入阻塞,等待被其他线程take
        synchronousQueue.put("1");

    }
}
