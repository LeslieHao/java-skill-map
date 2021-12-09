package com.hh.skillcs.principles;

/**
 * 缓存的空间局部性
 *
 * @author HaoHao
 * @date 2021/5/20 2:41 下午
 */
public class CacheLocality {

    public static void main(String[] args) {
        int[] arr = new int[64 * 1024 * 1024 ];
        long start = System.nanoTime();
        // 循环
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
        }
        System.out.println((System.nanoTime() - start) / 1000000000.0);

        long start1 = System.nanoTime();
        // 循环2
        for (int i = 0; i < arr.length; i += 16) {
            arr[i] *= 3;
        }
        System.out.println((System.nanoTime() - start1) / 1000000000.0);

    }
}
