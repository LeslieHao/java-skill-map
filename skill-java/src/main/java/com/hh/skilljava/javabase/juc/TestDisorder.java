package com.hh.skilljava.javabase.juc;

/**
 * 乱序执行测试
 *
 * @author HaoHao
 * @date 2022/2/12 2:10 下午
 */
public class TestDisorder {
    static int a;
    static int b;
    static int x;
    static int y;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 && y == 0) {
                System.out.println(i);
                break;
            }
        }
    }
}
