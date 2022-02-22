package com.hh.skilljava.develop.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author HaoHao
 * @date 2022/2/20 10:56 下午
 */
public class Server {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup(1);

        EventLoopGroup worker = new NioEventLoopGroup();

        // bizGroup 处理业务
        DefaultEventExecutorGroup bizGroup = new DefaultEventExecutorGroup(
                Runtime.getRuntime().availableProcessors() * 2,
                r -> new Thread(r, "biz-worker-group-%d")
        );
        bootstrap.group(boss, worker);

        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 开始tcp 底层的心跳
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                // 通道处理流水线
                ChannelPipeline pipeline = ch.pipeline();
                // 30 秒之内没有收到客户端请求的话就关闭连接
                pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                pipeline.addLast(bizGroup, new ServerHandler());
            }
        });
        bootstrap.bind(8888);
    }
}
