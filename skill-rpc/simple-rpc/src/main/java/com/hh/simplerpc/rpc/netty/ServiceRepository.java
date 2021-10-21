package com.hh.simplerpc.rpc.netty;

import cn.hutool.core.util.ClassUtil;
import com.hh.simplerpc.biz.annotations.ServiceImpl;
import com.hh.simplerpc.biz.serviceimpl.RpcServiceImpl;
import javassist.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 服务仓库
 *
 * @author HaoHao
 * @date 2021/9/26 8:20 下午
 */
public class ServiceRepository {

    public static final String SERVICE_FOLDER = "com.hh.simplerpc.biz.serviceimpl";

    private static ServiceRepository repository;

    private String f = "";

    public static ServiceRepository getInstance() {
        if (repository == null) {
            repository = new ServiceRepository();
        }
        return repository;
    }

    /**
     * 初始化服务
     */
    public void init() throws NotFoundException, CannotCompileException, IOException {
        // 扫描包里所有的标记为ServiceImpl 的 class
        Set<Class<?>> classSet = ClassUtil.scanPackage(SERVICE_FOLDER,
                cls -> cls.isAnnotationPresent(ServiceImpl.class));

        ClassPool classPool = ClassPool.getDefault();

        // 遍历发布
        for (Class<?> cls : classSet) {
            // 添加invoke 方法
            String methodBody = makeMethodBody(cls);

            CtClass ctClass = classPool.get(cls.getName());
            // 生成方法
            CtMethod invokeMethod = CtMethod.make(methodBody, ctClass);
            // 添加方法
            ctClass.addMethod(invokeMethod);

            byte[] bytes = ctClass.toBytecode();


        }
    }

    private static String makeMethodBody(Class<?> cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("public Object invoke(com.hh.simplerpc.rpc.netty.RpcRequestContext context) {");
        sb.append("String methodName = context.getMethodName();");

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            sb.append("if(methodName.equalsIgnoreCase(\"");
            sb.append(method.getName());
            sb.append("\")){return ");
            sb.append(method.getName());
            sb.append("(context);}");
        }

        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();

        // 添加invoke 方法
        String methodBody = makeMethodBody(RpcServiceImpl.class);

        CtClass ctClass = classPool.get(RpcServiceImpl.class.getName());
        // 生成方法
        CtMethod invokeMethod = CtMethod.make(methodBody, ctClass);
        // 添加方法
        ctClass.addMethod(invokeMethod);

        byte[] bytes = ctClass.toBytecode();

        // 输出class 文件
        File f = new File("/Users/haohao/Desktop/rpc.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(bytes);
        fout.close();
    }


}
