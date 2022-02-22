package com.hh.skilljava.javabase.juc;

import lombok.SneakyThrows;

/**
 * @author HaoHao
 * @date 2021/3/16 4:21 下午
 */
public class TestVolatile {

    volatile boolean flag = true;

    void test() {
        System.out.println("start~");
        int i = 0;
        // 这里无法跳出循环是因为jit 优化,不是可见性问题
        // volatile 还可以禁止jit 优化
        // -Djava.compiler=NONE
        while (flag) {
            i++;
        }
        System.out.println("end~,i=:" + i);
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile test = new TestVolatile();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.test();
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(100);
                test.flag = false;
                System.out.println("flag set false");
            }
        }).start();
    }
}
