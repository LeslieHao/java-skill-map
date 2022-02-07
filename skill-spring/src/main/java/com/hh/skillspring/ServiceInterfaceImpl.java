package com.hh.skillspring;

import lombok.extern.slf4j.Slf4j;

/**
 * @author HaoHao
 * @date 2021/9/27 2:35 下午
 */

@Slf4j
//@Service("service")
public class ServiceInterfaceImpl implements ServiceInterface {

    @Override
    public void say() {
        log.info("111111");
    }
}
