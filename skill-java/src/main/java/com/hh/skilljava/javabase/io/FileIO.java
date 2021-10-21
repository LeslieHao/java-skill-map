package com.hh.skilljava.javabase.io;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author HaoHao
 * @date 2021/9/24 4:56 下午
 */
public class FileIO {

    public static final String PATH = "/Users/haohao/Desktop/write.txt";

    @Test
    public void testWrite() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(PATH);
        fileOutputStream.write("aaaa".getBytes(StandardCharsets.UTF_8));
        fileOutputStream.flush();
    }

    @Test
    public void testRead() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(PATH);
        byte[] bytes = new byte[4];
        fileInputStream.read(bytes);
        System.out.println(new String(bytes));
    }

}
