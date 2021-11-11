package com.hh.skilljava.javabase.juc.threadtool;

import lombok.SneakyThrows;

/**
 * 父子线程
 *
 * @author HaoHao
 * @date 2021/11/8 6:58 下午
 */
public class ParentChildThread {

    /**
     * InheritableThreadLocal可以实现数据的继承，
     * (在创建子线程的时候 拷贝了一份数据),所以子线程的修改并不会影响父线程的数据,
     * 父线程在创建子线程之后的修改也不会影响子线程的数据
     *
     * 场景受限,项目中通常使用线程池,并不会完全都是创建新的子线程
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("init value");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
                threadLocal.set("change value");
                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        Thread.sleep(100);
                        System.out.println(threadLocal.get());
                    }
                }).start();
            }
        }).start();
        Thread.sleep(100);
        System.out.println(threadLocal.get());
    }

}
