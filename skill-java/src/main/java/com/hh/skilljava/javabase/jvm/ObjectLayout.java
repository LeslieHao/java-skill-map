package com.hh.skilljava.javabase.jvm;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 对象布局
 *
 * @author HaoHao
 * @date 2022/2/8 2:14 下午
 */

@Slf4j
public class ObjectLayout {


    public static void main(String[] args) {
        Person obj = new Person();
        log.info(ClassLayout.parseInstance(obj).toPrintable());
    }
}
