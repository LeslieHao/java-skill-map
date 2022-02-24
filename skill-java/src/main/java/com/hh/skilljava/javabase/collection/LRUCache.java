package com.hh.skilljava.javabase.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author HaoHao
 * @date 2022/2/22 11:26 上午
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {


    private final int cacheSize;

    public LRUCache(int cacheSize) {
        super(cacheSize, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > cacheSize;
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(3);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);

        // 为何了一个链表,在get 的时候吧数据移到链表队尾.
        //lruCache.get(2);
        //lruCache.get(1);
        lruCache.put(4, 4);
        System.out.println(lruCache);

    }
}
