package com.hh.skillspring;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

@SpringBootApplication
public class SkillSpringApplication implements ApplicationContextAware {

    @Resource
    ServiceInterface serviceInterface;

    @Resource
    BusinessComponent businessComponent;

    public static void main(String[] args) {
        SpringApplication.run(SkillSpringApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 如果没有配置增强,bean为原始类型,没有经过代理
        // 新版springboot 默认使用cglib
        serviceInterface.say();

        businessComponent.doBiz();

    }
}
