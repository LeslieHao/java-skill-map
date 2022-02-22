package com.hh.grpc.api;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author HaoHao
 * @date 2022/2/22 11:09 上午
 */
public class ProtoTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        RPCDateRequest request = RPCDateRequest.newBuilder()
                .setUserName("hao ren")
                .build();
        // 序列化为二进制
        byte[] bytes = request.toByteArray();

        // 反序列化
        RPCDateRequest requestSerial = RPCDateRequest.parseFrom(bytes);

        System.out.println(requestSerial.getUserName());
    }
}
