package com.hh.skilljava.javabase.bytecode.ASM;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.PrintStream;

/**
 * @author HaoHao
 * @date 2021/1/5 8:51 下午
 */
public class ChangeClassVisitor extends ClassVisitor {

    public ChangeClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("printSomething")) {
            return new MyMethodVisitor(Opcodes.ASM5, methodVisitor);
        }
        return methodVisitor;
    }


    static class MyMethodVisitor extends MethodVisitor {


        public MyMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        // 开始被访问
        @Override
        public void visitCode() {
            insertPrint(mv, "start");
            super.visitCode();
        }

        // 每一条指令被执行时
        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN) {
                // return code
                insertPrint(mv, "end");
            }
            super.visitInsn(opcode);
        }

        // 插入字节码
        private void insertPrint(MethodVisitor mv, String msg) {
            mv.visitFieldInsn(
                    Opcodes.GETSTATIC,
                    Type.getInternalName(System.class),
                    "out",
                    Type.getDescriptor(PrintStream.class)
            );
            mv.visitLdcInsn(msg);
            mv.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    Type.getInternalName(PrintStream.class),
                    "println",
                    "(Ljava/lang/String;)V",
                    false);
        }
    }
}
