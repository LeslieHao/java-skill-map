package com.hh.skilljava.javabase.lambda;

/**
 * @author HaoHao
 * @date 2021/11/18 7:50 下午
 */

public class Lambda {

    public void addListener(Listener listener) {
        listener.listen("ok");
    }

    public void doWork(){
        Listener listener = new Listener() {
            @Override
            public void listen(String str) {

            }
        };
        addListener(System.out::println);
    }
}
