package com.hh.skilljava.javabase.io;

import lombok.SneakyThrows;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 同步阻塞IO 实现服务端
 *
 * @author HaoHao
 * @date 2021/8/11 8:32 下午
 */
public class NormalIO {


    public static void main(String[] args) throws IOException, InterruptedException {
        NormalServer server = new NormalServer(new ServerSocket(8888));
        server.start();
        Socket client = new Socket(InetAddress.getLocalHost(), server.getPort());
        OutputStream outputStream = client.getOutputStream();
        for (int i = 0; i < 10; i++) {
            outputStream.write(1);
        }
    }

    static class NormalServer extends Thread {

        private final ServerSocket serverSocket;

        ThreadPoolExecutor serverExecutor =
                new ThreadPoolExecutor(17, 100, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1000), r -> {
                    Thread thread = new Thread(r);
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


    static class RequestHandler implements Runnable {

        private final Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @SneakyThrows
        @Override
        public void run() {
            // 去读客户端传来的数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[5];
            while (true) {
                Thread.sleep(300);
                int read = inputStream.read(bytes);
                System.out.println(read);
            }
        }
    }

}
