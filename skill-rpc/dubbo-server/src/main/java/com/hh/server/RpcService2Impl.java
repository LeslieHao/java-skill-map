package com.hh.server;

import com.hh.api.RpcService2;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author HaoHao
 * @date 2021/8/13 4:43 下午
 */
@Slf4j
@DubboService(version = "1.0")
public class RpcService2Impl implements RpcService2 {
    @Override
    public String sayAnything(String str) {
        log.info("sayAnything:{}", str);
        return str + ",received";
    }
}
