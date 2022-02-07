package com.hh.grpc.api;

import io.grpc.stub.StreamObserver;

/**
 * @author HaoHao
 * @date 2022/2/7 1:14 下午
 */
public class RPCDataServiceImpl extends RPCDateServiceGrpc.RPCDateServiceImplBase {


    @Override
    public void getDate(RPCDateRequest request, StreamObserver<RPCDateResponse> responseObserver) {

        // 入参
        String userName = request.getUserName();
        System.out.println("geData: " + userName);

        RPCDateResponse response = RPCDateResponse.newBuilder()
                .setServerDate("respons!!!")
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();

    }
}
