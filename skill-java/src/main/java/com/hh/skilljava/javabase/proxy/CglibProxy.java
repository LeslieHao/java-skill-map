package com.hh.skilljava.javabase.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * Spring如何选择用JDK还是CGLiB？
 *
 * 1、当Bean实现接口时，Spring就会用JDK的动态代理。
 * 2、当Bean没有实现接口时，Spring使用CGlib是实现。
 * 3、可以强制使用CGlib（在spring配置中加入<aop:aspectj-autoproxy proxy-target-class="true"/>）。
 *
 *
 * @author HaoHao
 * @date 2021/4/15 3:28 下午
 */
public class CglibProxy {

    public static Object getProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
         enhancer.setCallback(new MethodInterceptor() {
             @Override
             public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                 System.out.println("cglib intercept start...");
                 Object result = methodProxy.invokeSuper(o, objects);
                 System.out.println("cglib intercept end...");
                 return result;
             }
        });
        return enhancer.create();
    }

    public static void main(String[] args) {
        UserDaoImpl proxy = (UserDaoImpl) CglibProxy.getProxy(new UserDaoImpl());
        proxy.save();
    }
}
