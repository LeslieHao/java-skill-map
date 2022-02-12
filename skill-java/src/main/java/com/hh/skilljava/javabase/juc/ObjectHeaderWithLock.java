package com.hh.skilljava.javabase.juc;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author HaoHao
 * @date 2022/2/11 10:46 下午
 */

@Slf4j
public class ObjectHeaderWithLock {

    public static void main(String[] args) throws InterruptedException {
        //Person obj = new Person();
        Thread.sleep(5000);
        Object obj = new Object();
        log.info(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }
}
