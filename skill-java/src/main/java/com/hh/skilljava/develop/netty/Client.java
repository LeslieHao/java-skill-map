package com.hh.skilljava.develop.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author HaoHao
 * @date 2022/2/20 11:05 下午
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup clientGroup = new NioEventLoopGroup();

        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        // 5. 连接服务器，注意这里全部都是异步的
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
        // 6. 关闭通道连接监听
        channelFuture.channel().closeFuture().sync();
    }
}
