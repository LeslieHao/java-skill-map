package com.hh.grpc.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author HaoHao
 * @date 2022/2/7 1:21 下午
 */
public class Client {

    public static void main(String[] args) {
        // 拿到channel
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 10001)
                .usePlaintext()
                .build();

        // 拿到代理对象
        RPCDateServiceGrpc.RPCDateServiceBlockingStub service = RPCDateServiceGrpc.newBlockingStub(managedChannel);

        // 构建request
        RPCDateRequest request = RPCDateRequest.newBuilder()
                .setUserName("zhang yi yi")
                .build();
        // 请求
        RPCDateResponse date = service.getDate(request);

        System.out.println(date.getServerDate());

    }

}
