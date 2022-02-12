package com.hh.skilljava.javabase.jvm;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author HaoHao
 * @date 2022/2/9 4:01 下午
 */
public class GCTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int i = scanner.nextInt();
            if (i == 1) {
                for (int j = 0; j < 1000000; j++) {
                    Object o = new Object();
                }
                System.out.println("over");
            } else {
                System.gc();
            }
        }
    }
}
