package com.hh.skilljava.javabase;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author HaoHao
 * @date 2021/4/13 5:30 下午
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            // 重写loadClass 方法 破坏双亲委派,重复加载
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1)+".class";
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
        };
        Object obj = myLoader.loadClass("com.hh.skilljava.javabase.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(obj instanceof ClassLoaderTest);
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(obj.getClass().getClassLoader().getParent());
        System.out.println(obj.getClass().getClassLoader().getParent().getParent());
        System.out.println(obj.getClass().getClassLoader().getParent().getParent().getParent());

    }
}
