package com.hh.skilljava.javabase.juc.threadtool;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author HaoHao
 * @date 2021/11/8 7:27 下午
 */

@Slf4j
public class TransThreadLocal {

    static Executor executor =
            new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
                    r -> new Thread(r, "m-t-" + ThreadLocalRandom.current().nextInt(10)));


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("2");
                }
            });
        }
        TransmittableThreadLocal<String> ttl = new TransmittableThreadLocal<>();
        ttl.set("init value");
        executor.execute(TtlRunnable.get(() -> {
            log.info(ttl.get());
            ttl.set("change value");
        }));

        //for (int i = 0; i < 10; i++) {
        //    executor.execute(new Runnable() {
        //        @Override
        //        public void run() {
        //            log.info(ttl.get());
        //        }
        //    });
        //}

    }
}
