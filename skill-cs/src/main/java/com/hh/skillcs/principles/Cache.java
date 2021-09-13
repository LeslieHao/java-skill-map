package com.hh.skillcs.principles;


import lombok.SneakyThrows;

/**
 * @author HaoHao
 * @date 2021/5/24 2:42 下午
 */
public class Cache {

    private static volatile int COUNTER = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();

    }

    static class ChangeListener extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            int threadValue = COUNTER;
            while (threadValue < 5) {
                if (threadValue != COUNTER) {
                    System.out.println("Got Change for COUNTER : " + COUNTER + "");
                    threadValue = COUNTER;
                }
                Thread.sleep(500);
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int threadValue = COUNTER;
            while (COUNTER < 5) {
                System.out.println("Incrementing COUNTER to : " + (threadValue + 1) + "");
                COUNTER = ++threadValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
