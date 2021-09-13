package com.hh.skilljava.javabase.proxy;

/**
 *
 * 缺点
 *
 * 冗余。由于代理对象要实现与目标对象一致的接口，会产生过多的代理类。
 * 不易维护。一旦接口增加方法，目标对象与代理对象都要进行修改。
 *
 * 静态代理在编译时就已经实现，编译完成后代理类是一个实际的class文件
 * 动态代理是在运行时动态生成的，即编译完成后没有实际的class文件，而是在运行时动态生成类字节码，并加载到JVM中
 *
 * @author HaoHao
 * @date 2021/4/15 11:55 上午
 */
public class StaticProxy implements UserDao {

    private final UserDao userDao;

    public StaticProxy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println("before");
        userDao.save();
        System.out.println("after");
    }

    public static void main(String[] args) {
        StaticProxy staticProxy = new StaticProxy(new UserDaoImpl());
        staticProxy.save();
    }
}
