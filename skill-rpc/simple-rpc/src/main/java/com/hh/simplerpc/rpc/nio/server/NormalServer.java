package com.hh.simplerpc.rpc.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author HaoHao
 * @date 2021/9/14 9:24 下午
 */
@Slf4j
public class NormalServer {

    private final Selector selector;

    public static final int PORT = 21888;

    public NormalServer(int port) throws IOException {
        log.info("server init,port:" + port);
        // open a channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // non block
        serverSocketChannel.configureBlocking(false);
        // 绑定tcp 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        this.selector = Selector.open();
        // 注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 初始化要发布的接口方法(创建代理类,字节码生成invoke 方法)


    }

    public void start() throws IOException, InterruptedException {
        log.info("~~~~~~ server start ~~~~~~");
        while (true) {
            // select 事件
            int selectRes = selector.select();
            log.info("server select return :" + selectRes);
            // 返回有事件到达的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历,处理时间
            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isAcceptable()) {
                    // OP_ACCEPT,有客户端连接
                    log.info("server OP_ACCEPT");
                    // 客户端连接后建立的socket
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = acceptChannel.accept();
                    // 设置非阻塞
                    channel.configureBlocking(false);
                    // 注册可读事件
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // OP_READ,有数据到达(客户端发送请求)
                    log.info("server OP_READ");
                    // 可读通道
                    SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                    // buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1 << 8);
                    // 读数据到buffer 中
                    readChannel.read(byteBuffer);
                    byteBuffer.flip();
                    // 拿出对应的方法,调用invoke


                }
            }
            // 必须清空,selectionKeys 是属于selector的,并不会每次返回新的集合
            selectionKeys.clear();
            Thread.sleep(2000);
        }
    }
}
