package com.hh.simplerpc.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * rpc 服务端(netty)
 *
 * @author HaoHao
 * @date 2021/9/24 8:47 下午
 */

@Slf4j
public class NettyServer {

    public static final int PORT = 26688;

    public static final String IP = "127.0.0.1";

    public void start() {
        // 服务引导器
        ServerBootstrap bootstrap = new ServerBootstrap();
        // EventLoopGroup 本质就是线程池
        // boss 负责处理连接事件
        EventLoopGroup boss = new NioEventLoopGroup(1);
        // worker 处理读写事件
        EventLoopGroup worker = new NioEventLoopGroup();
        // bizGroup 处理业务
        DefaultEventExecutorGroup bizGroup = new DefaultEventExecutorGroup(
                Runtime.getRuntime().availableProcessors() * 2,
                r -> new Thread(r, "biz-worker-group-%d")
        );
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    // TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    // Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 开始tcp 底层的心跳
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 通道处理流水线
                            ChannelPipeline pipeline = ch.pipeline();
                            // 30 秒之内没有收到客户端请求的话就关闭连接
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            // 协议编码 和解码
                            //p.addLast(new RpcMessageEncoder());
                            //p.addLast(new RpcMessageDecoder());
                            pipeline.addLast(bizGroup, new RequestHandler());
                        }
                    });
            bootstrap.bind(IP, PORT);
            // 发布接口


        } catch (Exception e) {
            log.error("server start exception", e);
        } finally {
            log.error("shutdown group");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            bizGroup.shutdownGracefully();
        }
    }

}
