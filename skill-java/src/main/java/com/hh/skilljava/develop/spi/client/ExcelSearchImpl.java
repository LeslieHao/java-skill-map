package com.hh.skilljava.develop.spi.client;

import com.hh.skilljava.develop.spi.server.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoHao
 * @date 2022/1/8 5:22 下午
 */
public class ExcelSearchImpl implements Search {
    @Override
    public List<String> search(String path) {
        System.out.println("do excel search");
        return new ArrayList<>();
    }

}
