package com.hh.skilljava.develop.timetriger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author HaoHao
 * @date 2021/11/27 2:23 下午
 */
public class ScheduledThreadPool {


    static ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "mT-" + System.currentTimeMillis());
        }
    });



    public static void main(String[] args) {
        // 底层是一个自动排序的delayQueue, 会根据执行时间排序
        // 线程池的worker 会从delayQueue 中take,拿出队首,然后计算等待时间,然后调用condition.awaitNanos 挂起自己
        // 这是如果有其他任务入队且入队后排在队首,会调用condition.signal,唤醒一个线程,来重新执行队首的任务
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("task run ~");
            }
        }, 5000, TimeUnit.MILLISECONDS);

        //scheduledExecutorService.schedule(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("task run ~");
        //    }
        //}, 2000, TimeUnit.MILLISECONDS);
    }

}
