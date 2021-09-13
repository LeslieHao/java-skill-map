package com.hh.skillcs.principles;

import java.util.Arrays;
import java.util.Random;

/**
 * 分支预测
 *
 * @author HaoHao
 * @date 2021/5/17 8:55 下午
 */
public class BranchGuess {

    /**
     * 分支预测:假的分支不发生
     *
     * @param args
     */
    public static void main(String args[]) {
        int arraySize = 32768;
        int data[] = new int[arraySize];

        Random rnd = new Random(0);
        for (int c = 0; c < arraySize; ++c) {
            data[c] = rnd.nextInt() % 256;
        }

        // !!! With this, the next loop runs faster
        // 排序后 会降低分支预测的错误率
        Arrays.sort(data);

        // Test
        long start = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < 100000; ++i) {
            for (int c = 0; c < arraySize; ++c) {   // Primary loop
                if (data[c] >= 128) {
                    sum += data[c];
                }
            }
        }

        System.out.println((System.nanoTime() - start) / 1000000000.0);
        System.out.println("sum = " + sum);
    }
}
