package com.hh.skilljava.javabase.juc;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author HaoHao
 * @date 2021/3/9 5:28 下午
 */
public class BlockQueueTest {

    BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

    @Test
    public void testAdd() {
        // queue 满了会抛出 java.lang.IllegalStateException: Queue full
        for (int i = 0; i < 11; i++) {
            blockingQueue.add(i);
            System.out.println("add:" + i);
        }
    }

    @Test
    public void testPut() throws InterruptedException {
        // queue 满了会阻塞
        for (int i = 0; i < 11; i++) {
            blockingQueue.put(i);
            System.out.println("add:" + i);
        }
    }

    @Test
    public void testOffer() {
        // queue 满了会 return false
        for (int i = 0; i < 11; i++) {
            boolean r = blockingQueue.offer(i);
            System.out.println("add:" + i + ",result:" + r);
        }
    }
    @Test
    public void testOfferTimeOut() throws InterruptedException {
        // queue 满了会 return false
        for (int i = 0; i < 11; i++) {
            boolean r = blockingQueue.offer(i,1000, TimeUnit.MILLISECONDS);
            System.out.println("add:" + i + ",result:" + r);
        }
    }

    @Test
    public void testTake() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            blockingQueue.add(i);
        }
        // queue 空了会阻塞
        for (int i = 0; i < 11; i++) {
            System.out.println("take:" + blockingQueue.take());
        }
    }
    @Test
    public void testPoll() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            blockingQueue.add(i);
        }
        // queue 空了会返回空
        for (int i = 0; i < 11; i++) {
            System.out.println("poll:" + blockingQueue.poll());
        }
    }
    @Test
    public void testPeek() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            blockingQueue.add(i);
        }
        // queue 空了会返回空
        for (int i = 0; i < 11; i++) {
            // 偷看队首
            System.out.println("peek:" + blockingQueue.peek());
        }
    }

}
