package com.hh.skillspring;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HaoHao
 * @date 2021/9/27 2:35 下午
 */

@Service
public class ServiceInterfaceImpl implements ServiceInterface {

    @Resource
    private BusinessComponent businessComponent;

    @Override
    public void say() {
        businessComponent.doBiz();
    }
}
