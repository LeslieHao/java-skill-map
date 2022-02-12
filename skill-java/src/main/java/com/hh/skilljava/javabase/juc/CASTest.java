package com.hh.skilljava.javabase.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author HaoHao
 * @date 2022/2/11 8:35 下午
 */
public class CASTest {

    @Test
    public void test(){
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    @Test
    public void testABA(){
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(1, 0);
        // 版本号
        int stamp = stampedReference.getStamp();
        // 改成2 在改回1
        boolean b = stampedReference.compareAndSet(1, 2, stamp, stamp + 1);
        stamp = stampedReference.getStamp();
        boolean c = stampedReference.compareAndSet(2, 1, stamp, stamp + 1);
        // 再改成2
        boolean d = stampedReference.compareAndSet(1, 2, stamp, stamp + 1);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
