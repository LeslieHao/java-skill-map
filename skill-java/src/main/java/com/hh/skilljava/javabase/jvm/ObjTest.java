package com.hh.skilljava.javabase.jvm;

/**
 * @author HaoHao
 * @date 2022/2/8 9:39 下午
 */
public class ObjTest {

    int num = 10;

    public ObjTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(num);
            }
        }).start();
    }

    public static void main(String[] args) {
        new Object();
    }
}
