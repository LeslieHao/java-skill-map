package com.hh.skilljava.javabase.juc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author HaoHao
 * @date 2021/12/23 3:53 下午
 */
@Slf4j
public class InterruptTest {

    /**
     * wait()
     * join()
     * sleep()
     * 等都可以响应中断
     *
     * 线程池 中的 shutdownNow 方法会遍历线程池中的工作线程并调用线程的 interrupt 方法来中断线程
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error("catch interruptedException", e);
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
