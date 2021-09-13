package com.hh.client;

import com.hh.api.RpcService;
import com.hh.api.RpcService2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HaoHao
 * @date 2021/8/17 8:24 下午
 */

@EnableDubbo
@RestController
@SpringBootApplication
public class ClientApplication {

    @DubboReference(version = "${rpc.service.version}", url = "${rpc.service.url}")
    private RpcService rpcService;

    @DubboReference(version = "${rpc.service.version}", url = "${rpc.service.url}")
    private RpcService2 rpcService2;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @RequestMapping("/say")
    public String say(String str) {
        return rpcService.sayWhat(str);
    }

    @RequestMapping("/say1")
    public String say1(String str) {
        return rpcService.saySomething(str);
    }

    @RequestMapping("/say2")
    public String say2(String str) {
        return rpcService2.sayAnything(str);
    }
}
