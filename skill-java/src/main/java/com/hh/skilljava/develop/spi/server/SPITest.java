package com.hh.skilljava.develop.spi.server;

import java.util.ServiceLoader;

/**
 * service provider interface
 *
 * @author HaoHao
 * @date 2022/1/8 5:18 下午
 */
public class SPITest {

    /**
     * 服务端默认会加载所有的客户端自己实现的插件 并调用
     */
    public static void main(String[] args) {
        ServiceLoader<Search> search = ServiceLoader.load(Search.class);
        for (Search s : search) {
            s.search("");
        }
    }

}
