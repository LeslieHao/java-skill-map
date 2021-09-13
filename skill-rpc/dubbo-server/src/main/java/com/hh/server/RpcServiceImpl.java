package com.hh.server;

import com.hh.api.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author HaoHao
 * @date 2021/8/13 4:43 下午
 */
@Slf4j
@DubboService(version = "1.0")
public class RpcServiceImpl implements RpcService {

    @Override
    public String sayWhat(String str) {
        log.info("sayWhat:{}", str);
        return str + ",received";
    }

    @Override
    public String saySomething(String str) {
        log.info("saySomething:{}", str);
        return str + ",received";
    }
}
