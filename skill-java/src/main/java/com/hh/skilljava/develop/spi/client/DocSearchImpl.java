package com.hh.skilljava.develop.spi.client;

import com.hh.skilljava.develop.spi.server.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端去实现服务端接口(例如skywalking 的自定义插件,还有dubbo 的扩展点)
 * 然后写入META-INF/services 的对应接口的文件中
 *
 * @author HaoHao
 * @date 2022/1/8 5:22 下午
 */
public class DocSearchImpl implements Search {
    @Override
    public List<String> search(String path) {
        System.out.println("do doc search");
        return new ArrayList<>();
    }

}
