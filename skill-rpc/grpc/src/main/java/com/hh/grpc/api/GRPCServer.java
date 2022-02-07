package com.hh.grpc.api;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author HaoHao
 * @date 2022/2/7 1:24 下午
 */
public class GRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(10001)
                .addService(new RPCDataServiceImpl())
                .build()
                .start();
        server.awaitTermination();
    }
}
