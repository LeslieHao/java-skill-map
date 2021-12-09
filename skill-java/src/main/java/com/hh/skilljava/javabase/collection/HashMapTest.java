package com.hh.skilljava.javabase.collection;

import java.util.HashMap;

/**
 * @author HaoHao
 * @date 2021/12/8 9:42 下午
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(3, 2);
        for (int i = 0; i < 10; i++) {
            map.put("k" + i, "v" + i);
        }
    }
}
