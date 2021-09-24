package com.hh.skilljava.javabase.bytecode.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author HaoHao
 * @date 2021/1/5 4:46 下午
 */
public class Interceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            System.out.println(method + ":cost:" + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
