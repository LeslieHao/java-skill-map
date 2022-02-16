package com.hh.skilljava.javabase.proxy;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author HaoHao
 * @date 2022/2/15 11:29 上午
 */
public class JavassistProxy {

    private Object target;

    public JavassistProxy(Object target) {
        this.target = target;
    }

    public Object getProxy() throws IllegalAccessException, InstantiationException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(target.getClass());
        proxyFactory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method method) {
                return "save".equals(method.getName());
            }
        });

        MethodHandler methodHandler = new MethodHandler() {
            @Override
            public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
                System.out.println("JavassistProxy~");
                return method.invoke(target, objects);
            }
        };
        Proxy proxy = (Proxy) proxyFactory.createClass().newInstance();
        proxy.setHandler(methodHandler);
        return proxy;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        JavassistProxy javassistProxy = new JavassistProxy(new UserDaoImpl());
        UserDao proxy = (UserDao) javassistProxy.getProxy();
        proxy.save();
    }
}
