package com.hh.skillspring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HaoHao
 * @date 2021/9/27 2:42 下午
 */

@Slf4j
@Aspect
@Component
public class LogAspect {


    @Around(value = "@annotation(logAspect)")
    public Object methodAround(ProceedingJoinPoint joinPoint, LogAspectAnnotation logAspect) throws Throwable {
        log.info("methodAround,{}", joinPoint.getArgs());
        return joinPoint.proceed();
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LogAspectAnnotation {

    }
}
