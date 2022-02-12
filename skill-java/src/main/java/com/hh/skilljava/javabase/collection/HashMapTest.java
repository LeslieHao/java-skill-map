package com.hh.skilljava.javabase.collection;

import cn.hutool.core.util.ReflectUtil;

import java.util.HashMap;

/**
 * @author HaoHao
 * @date 2021/12/8 9:42 下午
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(4, 2);
        for (int i = 0; i < 10; i++) {

            map.put("k" + i, "v" + i);

            int threshold = (int) ReflectUtil.getFieldValue(map, "threshold");
            float loadFactor = (float) ReflectUtil.getFieldValue(map, "loadFactor");
            int size = (int) ReflectUtil.getFieldValue(map, "size");
            System.out.println("----------");
            System.out.println("threshold:" + threshold);
            System.out.println("loadFactor:" + loadFactor);
            System.out.println("size:" + size);
            System.out.println("----------");
        }
    }
}
