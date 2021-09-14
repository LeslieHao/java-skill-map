package com.hh.skilljava.javabase.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author HaoHao
 * @date 2020/5/11 5:04 下午
 */
public class Server {

    private Selector selector;

    public Server(int port) throws IOException {
        System.out.println("server init,port:" + port);
        // open a channel
        // ServerSocketChannel 用来监听新进来的tcp 连接
        // 并不能传输数据
        // 一个新的连接到达ServerSocketChannel 时,会创建一个新的SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // non block
        serverSocketChannel.configureBlocking(false);
        // 绑定tcp 端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // get a selector
        // windows ->iocp
        // mac ->kqueue
        // linux ->epoll
        this.selector = Selector.open();
        /*
         OP_ACCEPT:ServerSocketChannel的有效事件,服务端收到客户端的一个连接请求会触发
         OP_CONNECT:SocketChannel的有效事件 连接就绪事件,表示服务端监听到了客户端的连接
         OP_READ:SocketChannel的有效事件 读就绪事件,表示channel 中有可读的数据
         OP_WRITE:SocketChannel的有效事件 写就绪事件,表示已经可以向通道中写数据了
         */


        // 将channel 注册到selector(OP_ACCEPT 事件),当有client 连接时会出发事件
        // 当该事件到达时, selector.select() 会返回,否则将阻塞
        // 一个selector 可以管理多个socket
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException, InterruptedException {
        System.out.println("server listen start~");
        while (true) {
            // select 事件
            int selectRes = selector.select();
            System.out.println("server select return :" + selectRes);
            // 返回有事件到达的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历,处理时间
            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isAcceptable()) {
                    // OP_ACCEPT,有客户端连接
                    System.out.println("server OP_ACCEPT");
                    // 客户端连接后建立的socket
                    ServerSocketChannel acceptChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = acceptChannel.accept();
                    // 设置非阻塞
                    channel.configureBlocking(false);
                    // 注册可读事件
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // OP_READ,有数据到达(客户端发送请求)
                    System.out.println("server OP_READ");
                    // 可读通道
                    SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                    // buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1 << 8);
                    // 读数据到buffer 中
                    readChannel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println("server 收到客户端消息:" + new String(byteBuffer.array(), StandardCharsets.UTF_8).trim());
                    // 回调客户端
                    readChannel.write(ByteBuffer.wrap("服务端已经收到消息".getBytes(StandardCharsets.UTF_8)));
                }
            }
            // 必须清空,selectionKeys 是属于selector的,并不会每次返回新的集合
            selectionKeys.clear();
            Thread.sleep(2000);
        }
    }

}
