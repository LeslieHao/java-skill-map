package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author HaoHao
 * @date 2021/12/15 4:08 下午
 */
@Slf4j
public class LockSupportTest {

    static class FirstBlocker {

        private Thread thread;

        public FirstBlocker(Thread thread) {
            this.thread = thread;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }
    }

    private static FirstBlocker BLOCKER;


    public static void main(String[] args) {
        log.info("start~");
        BLOCKER = new FirstBlocker(Thread.currentThread());
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                LockSupport.unpark(BLOCKER.getThread());
            }
        }).start();

        LockSupport.park(BLOCKER);
        log.info("end~");
    }
}
