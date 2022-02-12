package com.hh.skilljava.javabase.juc;

/**
 * @author HaoHao
 * @date 2021/3/16 4:21 下午
 */
public class TestVolatile {

    volatile boolean flag = true;

    void test() {
        System.out.println("start~");
        while (flag) {

        }
        System.out.println("end~");
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile test = new TestVolatile();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.test();
            }
        }).start();
        Thread.sleep(1000);
        test.flag = false;
    }
}
