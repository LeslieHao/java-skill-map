package com.hh.skilljava.javabase.classload;

import com.hh.skilljava.javabase.bytecode.javassist.ClassCreater;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author HaoHao
 * @date 2021/4/13 5:30 下午
 */
public class ClassLoaderTest extends ClassLoader {


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = getClass().getResourceAsStream(fileName);
            if (is == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }


    @Test
    public void testCreateAndLoad() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CannotCompileException, NotFoundException, IOException {
        CtClass cc = ClassCreater.getCc();
        byte[] bytes = cc.toBytecode();
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if ("com.hh.bytecode.javassist.YaoMing".equals(name)) {
                    return defineClass(name, bytes, 0, bytes.length);
                } else {
                    return super.loadClass(name);
                }
            }
        };

        Class<?> cls = classLoader.loadClass("com.hh.bytecode.javassist.YaoMing");
        Object obj = cls.newInstance();
        // 反射调用
        Method desc = cls.getMethod("desc");
        desc.invoke(obj);

    }


    public static void main(String[] args) throws Exception {
        //ClassLoaderTest classLoader = new ClassLoaderTest();
        //
        //Object obj = classLoader.loadClass("com.hh.skilljava.javabase.classload.MyObject").newInstance();
        //
        //System.out.println("obj classLoader: " + obj.getClass().getClassLoader());
        //System.out.println("obj classLoader的父加载器: " + obj.getClass().getClassLoader().getParent());
        //System.out.println("obj classLoader的爷爷加载器: " + obj.getClass().getClassLoader().getParent().getParent());
        //
        //// Bootstrap ClassLoader是由C++编写的,并不是Java 的一个类,所以无法获取,返回空
        //System.out.println("obj classLoader的曾爷爷加载器: " + obj.getClass().getClassLoader().getParent().getParent().getParent());
        //System.out.println("class classloader: " + MyObject.class.getClassLoader());
        //System.out.println("同一类不同加载器,是否instanceof: " + (obj instanceof MyObject));

        System.out.println("未加载");
        System.in.read();
        System.out.println(MyObject.class);
        System.in.read();
        MyObject myObject = new MyObject();
        System.out.println("已初始化");
        System.in.read();
    }
}
