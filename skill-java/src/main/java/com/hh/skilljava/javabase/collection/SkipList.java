package com.hh.skilljava.javabase.collection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HaoHao
 * @date 2022/2/17 2:41 下午
 */
public class SkipList {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> skipListSet = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 100; i++) {
            skipListSet.put(i, i);
        }
        System.out.println(skipListSet);
    }
}
