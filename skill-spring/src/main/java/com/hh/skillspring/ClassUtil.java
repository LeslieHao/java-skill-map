package com.hh.skillspring;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author HaoHao
 * @date 2022/1/12 2:32 下午
 */
public class ClassUtil {

    public static void generate(Class cls, String name) throws IOException {
        // 字节码写入文件
        ClassReader classReader = new ClassReader(cls.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(classWriter, ClassReader.SKIP_DEBUG);
        byte[] bytes = classWriter.toByteArray();
        // 输出class 文件
        File f = new File("file/" + name + ".class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(bytes);
        fout.close();
    }

    public static void generateProxy(Class cls, String name) throws IOException {
        byte[] bytes = ProxyGenerator.generateProxyClass(cls.getName(), new Class[]{});
        // 输出class 文件
        File f = new File("file/" + name + ".class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(bytes);
        fout.close();
    }
}
