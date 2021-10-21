package com.hh.simplerpc.biz.serviceimpl;

import com.hh.api.RpcService;
import com.hh.simplerpc.biz.annotations.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author HaoHao
 * @date 2021/9/26 8:29 下午
 */

@Slf4j
@ServiceImpl
public class RpcServiceImpl implements RpcService {

    @Override
    public String sayWhat(String str) {
        log.info("sayWhat:{}", str);
        return str + "received!";
    }

    @Override
    public String saySomething(String str) {
        log.info("saySomething:{}", str);
        return "fuck the rpc";
    }

}
