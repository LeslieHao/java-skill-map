package com.hh.skilljava.javabase.bytecode.javassist;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author HaoHao
 * @date 2021/9/18 4:06 下午
 */
public class ClassCreater {

    @Test
    public void create() throws CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        CtClass cc = getCc();

        // 拿到class
        Class<?> cls = cc.toClass();
        Object obj = cls.newInstance();
        // 反射调用
        Method desc = cls.getMethod("desc");
        desc.invoke(obj);

        // 字节码
        //byte[] bytes = cc.toBytecode();

        // 写入文件
        //cc.writeFile("/Users/haohao/Desktop/YaoMing.class");
    }

    public static CtClass getCc() throws CannotCompileException, NotFoundException {
        ClassPool classPool = ClassPool.getDefault();

        // 创建一个空类
        CtClass cc = classPool.makeClass("com.hh.bytecode.javassist.YaoMing");

        // 1.新增一个String 类型字段
        CtField field = new CtField(classPool.get(String.class.getName()), "height", cc);
        // 设置标识符为私有
        field.setModifiers(Modifier.PRIVATE);
        // 添加并设置默认值
        cc.addField(field, CtField.Initializer.constant("226cm"));

        // 2.新增无参构造
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, cc);
        ctConstructor.setBody("{height=\"110cm\";}");
        cc.addConstructor(ctConstructor);

        // 新增方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "desc", new CtClass[]{}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(height);}");
        cc.addMethod(ctMethod);
        return cc;
    }


}
