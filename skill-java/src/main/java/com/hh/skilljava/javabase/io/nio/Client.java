package com.hh.skilljava.javabase.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author HaoHao
 * @date 2020/5/11 5:58 下午
 */
public class Client {

    private Selector selector;

    public Client(String ip, int port) throws IOException {
        System.out.println("client init,ip:" + ip + ",port:" + port);
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = Selector.open();
        // 连接服务端
        channel.connect(new InetSocketAddress(ip, port));
        // 注册到selector
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException, InterruptedException {
        System.out.println("client listen start~");
        while (true) {
            // select 事件
            int selectRes = selector.select();
            System.out.println("client select return :" + selectRes);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isConnectable()) {
                    System.out.println("client OP_CONNECT");
                    // OP_CONNECT
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 完成连接
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);
                    // 注册读事件到选择器
                    channel.register(selector, SelectionKey.OP_READ);
                    // 向客户端发送消息
                    channel.write(ByteBuffer.wrap("req data".getBytes(StandardCharsets.UTF_8)));
                } else if (selectionKey.isReadable()) {
                    // OP_READ,服务端数据返回
                    System.out.println("client OP_READ");
                    // 可读通道
                    SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                    // buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1 << 8);
                    // 读数据到buffer 中
                    readChannel.read(byteBuffer);
                    byteBuffer.flip();
                    // 收到返回数据
                    System.out.println("res data:" + new String(byteBuffer.array(), StandardCharsets.UTF_8).trim());
                }
            }
            selectionKeys.clear();
            Thread.sleep(2000);
        }
    }
}
