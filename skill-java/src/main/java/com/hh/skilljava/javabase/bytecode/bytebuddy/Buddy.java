package com.hh.skilljava.javabase.bytecode.bytebuddy;

import com.hh.skilljava.javabase.bytecode.bytebuddy.model.Curry;
import com.hh.skilljava.javabase.bytecode.bytebuddy.model.Jordan;
import com.hh.skilljava.javabase.bytecode.bytebuddy.model.Kobe;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author HaoHao
 * @date 2021/1/5 5:38 下午
 */
public class Buddy {

    /**
     * 生成已有类的子类
     */
    @Test
    public void makeClass() {
        // 动态生成一个类 继承自Object
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("BuddyOne")
                .make();
        Class<?> dynamicClass = dynamicType.load(getClass().getClassLoader()).getLoaded();
        System.out.println(dynamicClass.getName());
        System.out.println(dynamicClass.getSuperclass().getName());
    }


    /**
     * 重新加载类
     */
    @Test
    public void redefine() {
        ByteBuddyAgent.install();
        Jordan jordan = new Jordan();
        new ByteBuddy()
                .redefine(Kobe.class)
                .name(Jordan.class.getName())
                .make()
                .load(Jordan.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        jordan.say();

    }


    /**
     * 增加一个字段
     * @throws NoSuchFieldException
     */
    @Test
    public void defineField() throws NoSuchFieldException {
        ByteBuddyAgent.install();
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Curry.class)
                .defineField("score", Integer.class)
                .make();
        Class<?> curry = dynamicType.load(getClass().getClassLoader()).getLoaded();
        System.out.println(curry.getDeclaredField("score"));
    }

    /**
     * 委托方法调用
     */
    @Test
    public void delegation() throws IllegalAccessException, InstantiationException, IOException {
        Class<? extends Jordan> jordan = new ByteBuddy()
                .subclass(Jordan.class)
                .method(ElementMatchers.named("say"))
                .intercept(MethodDelegation.to(Kobe.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        jordan.newInstance().say();
    }




    /**
     * 动态生成一个类,并修改返回值
     */
    @Test
    public void dynamicClass() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicClass = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("toString changed by buddy~"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        System.out.println(dynamicClass.newInstance().toString());
    }


    private void toFile(Class<?> cls,String name) throws IOException {
        ClassReader classReader = new ClassReader(cls.getName());
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(classWriter, ClassReader.SKIP_DEBUG);
        byte[] bytes = classWriter.toByteArray();
        // 输出class 文件
        File f = new File("/Users/haohao/Desktop/"+name+".class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(bytes);
        fout.close();
    }

}
