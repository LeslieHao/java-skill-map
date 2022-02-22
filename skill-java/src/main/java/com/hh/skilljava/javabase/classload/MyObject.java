package com.hh.skilljava.javabase.classload;

/**
 * @author HaoHao
 * @date 2021/12/9 2:43 下午
 */
public class MyObject {

    static String str;
    {
        System.out.println("static print");
    }

    static final int a = 1;
}
