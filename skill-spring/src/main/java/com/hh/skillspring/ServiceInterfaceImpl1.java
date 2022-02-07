package com.hh.skillspring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author HaoHao
 * @date 2022/1/12 11:00 上午
 */
@Slf4j
@Service("service1")
public class ServiceInterfaceImpl1 implements ServiceInterface{
    @Override
    @LogAspect.LogAspectAnnotation
    public void say() {
        log.info("222222");
    }
}
