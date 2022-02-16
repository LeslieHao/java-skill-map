package com.hh.skilljava.javabase.proxy;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 底层使用字节码生成代理类(其实就是把静态代理模式代码 在运行时动态生成)
 * 和cglib(底层使用asm) 底层实际都是一样的
 *
 * 真实调用使用反射 method.invoke
 *
 * 必须基于接口
 * 生成的proxy 继承了proxy,由于java的单继承,所有只能基于接口
 * 而且代理接口比代理类要更加灵活
 *
 * @author HaoHao
 * @date 2021/4/15 12:00 下午
 */
public class JDKProxy {


    public static Object getProxyInstance(Object target) {

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before");
                Object result = method.invoke(target, args);
                System.out.println("after");
                return result;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        UserDaoImpl userDao = new UserDaoImpl();
        UserDao proxyInstance = (UserDao) JDKProxy.getProxyInstance(userDao);
        proxyInstance.save();

        byte[] bytes = ProxyGenerator.generateProxyClass("123", new Class[]{userDao.getClass()});

        File f = new File("/Users/haohao/Desktop/123.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(bytes);
        fout.close();
    }
}
