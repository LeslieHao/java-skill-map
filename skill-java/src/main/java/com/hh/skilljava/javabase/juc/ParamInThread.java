package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * 线程间传递参数
 *
 * @author HaoHao
 * @date 2021/11/8 4:06 下午
 */
@Slf4j
public class ParamInThread {

    interface CallBackHelper {
        String callBack(String param);
    }


    static class T1 extends Thread {

        private Exchanger<String> exchanger;

        public T1(Exchanger<String> exchanger) {
            this.exchanger = exchanger;
        }

        @SneakyThrows
        @Override
        public void run() {
            log.info("T1 start");
            Thread.sleep(2000);
            String res = exchanger.exchange("2 块钱");
            log.info("main res:{}", res);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        new T1(exchanger).start();
        String res = exchanger.exchange("农夫山泉");
        log.info("T1 res:{}", res);
    }


}
