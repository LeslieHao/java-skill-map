package com.hh.skillspring;

import org.springframework.stereotype.Service;

/**
 * @author HaoHao
 * @date 2021/9/27 2:35 下午
 */

@Service
public class ServiceInterfaceImpl implements ServiceInterface {

    @Override
    @LogAspect.LogAspectAnnotation
    public void say() {

    }
}
