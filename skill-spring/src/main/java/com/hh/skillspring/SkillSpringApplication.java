package com.hh.skillspring;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

@SpringBootApplication
public class SkillSpringApplication implements ApplicationContextAware {

    @Resource(name = "service1")
    private ServiceInterface serviceInterface;

    @Resource
    private BusinessComponent businessComponent;

    public static void main(String[] args) {
        // 写入JDK 动态代理的class 到本地
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        SpringApplication.run(SkillSpringApplication.class, args);
    }

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 如果没有配置增强,bean为原始类型,没有经过代理

        // 基于接口注入,JDK 动态代理
        // com.sun.proxy.$Proxy56
        serviceInterface.say();

        // 默认使用cglib
        // com.hh.skillspring.BusinessComponent$$EnhancerBySpringCGLIB$$f7215938
        businessComponent.doBiz();

        //ClassUtil.generate(serviceInterface.getClass(), "ServiceInterfaceImpl1");
        //ClassUtil.generate(businessComponent.getClass(), "BusinessComponent");
    }

}
