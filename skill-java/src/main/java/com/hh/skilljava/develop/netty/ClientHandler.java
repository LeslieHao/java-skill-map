package com.hh.skilljava.develop.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author HaoHao
 * @date 2021/9/26 5:46 下午
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: " + ctx);
        // 给服务器发送消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello ,服务器", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器说: " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址为: " + ctx.channel().remoteAddress());
    }
}
