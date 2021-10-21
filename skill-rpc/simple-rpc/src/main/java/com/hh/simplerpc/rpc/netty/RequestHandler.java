package com.hh.simplerpc.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author HaoHao
 * @date 2021/9/26 5:46 下午
 */
public class RequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // todo 协议 解码
        //super.channelRead(ctx, msg);

        // invoke 目标方法


    }
}
