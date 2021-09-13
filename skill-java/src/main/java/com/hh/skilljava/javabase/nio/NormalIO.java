package com.hh.skilljava.javabase.nio;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 同步阻塞IO 实现服务端
 *
 * @author HaoHao
 * @date 2021/8/11 8:32 下午
 */
public class NormalIO {

    public static void main(String[] args) throws IOException {
        NormalServer server = new NormalServer(new ServerSocket(0));
        server.start();
        Socket client = new Socket(InetAddress.getLocalHost(), server.getPort());
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.println("hello~");
        writer.flush();
    }

    static class NormalServer extends Thread {

        private final ServerSocket serverSocket;

        Executor serverExecutor =
                new ThreadPoolExecutor(17, 100, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1000), r -> {
                    Thread thread = new Thread();
                    thread.setName("server worker");
                    return thread;
                });

        public NormalServer(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public int getPort() {
            return serverSocket.getLocalPort();
        }

        /**
         * 如果连接数并不是非常多，只有最多几百个连接的普通应用，这种模式往往可以工作的很好。
         * 但是，如果连接数量急剧上升，这种实现方式就无法很好地工作了
         * 因为线程上下文切换开销会在高并发时变得很明显，这是同步阻塞方式的低扩展性劣势。
         * 如果连接很多 且只有部分连接活跃 多路复用更合适
         */
        @Override
        public void run() {
            try {
                while (true) {
                    // 有连接进来会返回,没有客户端连接会阻塞
                    Socket socket = serverSocket.accept();
                    System.out.println("accept~");
                    // 服务端请求处理器处理请求
                    serverExecutor.execute(new RequestHandler(socket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class RequestHandler extends Thread {

        private final Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @SneakyThrows
        @Override
        public void run() {
            // 去读客户端传来的数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                reader.lines().forEach(System.out::println);
                Thread.sleep(1000);
            }
        }
    }

}
