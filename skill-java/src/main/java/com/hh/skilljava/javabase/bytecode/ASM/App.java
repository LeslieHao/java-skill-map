package com.hh.skilljava.javabase.bytecode.ASM;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @author HaoHao
 * @date 2021/1/5 8:37 下午
 */
public class App {

    public static void main(String[] args) throws Exception {
        // 读取
        ClassReader classReader = new ClassReader("com.hh.skilljava.javabase.bytecode.ASM.OriginalClass");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // 处理
        ClassVisitor classVisitor = new ChangeClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

        byte[] data = classWriter.toByteArray();

        // 输出class 文件
        //File f = new File("/Users/haohao/IdeaProjects/m/java-skill-map/skill-java/src/main/java/com/hh/skilljava/javabase/bytecode/ASM/Original.class");
        //FileOutputStream fout = new FileOutputStream(f);
        //fout.write(data);
        //fout.close();

        OriginalClass original = new OriginalClass();
        original.printSomething();

    }
}
