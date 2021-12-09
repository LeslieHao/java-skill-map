package com.hh.skilljava.develop;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @author HaoHao
 * @date 2021/12/9 8:47 下午
 */
public class HashWheelTimerTest {

    public static void main(String[] args) throws InterruptedException {
        HashedWheelTimer wheel = new HashedWheelTimer();
        wheel.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("do~");
                Thread.sleep(10000);
            }
        }, 2, TimeUnit.SECONDS);

        Thread.sleep(1000);

        wheel.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("do~");
            }
        }, 3, TimeUnit.SECONDS);
    }
}
