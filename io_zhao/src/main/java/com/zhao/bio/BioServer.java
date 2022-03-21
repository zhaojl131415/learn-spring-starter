package com.zhao.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description 服务器端
 * @date 2020-04-04 10:42
 *
 * 空连接会造成资源浪费
 */
public class BioServer {

    // 服务端socket
    ServerSocket serverSocket;
    // 服务端处理器线程池
    ServerHandlerExecutorPool executorPool;

    public BioServer() {
        try {
            serverSocket = new ServerSocket(9090);
            executorPool = new ServerHandlerExecutorPool(10, 100);
            // 为了保证让main方法不会执行完而结束: 长连接
            while(true) {
                // 客户端socket: 这一步是阻塞的, 没有获取到客户端, 会一直阻塞在这
                Socket socket = serverSocket.accept();

                System.out.println("客户端:" + socket.getRemoteSocketAddress().toString() + "来了...");

                // 方案1: 直接调用: 会阻塞在read(), 阻塞其他连接
//                new BioServerHandler(socket).run();
                // 方案2: 新开子线程,异步调用, 每来一个新连接, 就需要创建一个子线程, 连接太多, 就会造成线程太多, 服务器扛不住
//                new Thread(new BioServerHandler(socket)).start();

                /**
                 * 方案3: 线程池异步调用, 虽然解决了新建太多子线程问题, 但是同时也限制了并发数, 线程池的最大线程数决定了同一时间的最大并发数
                 * @see BioServerHandler#run()
                 */
                executorPool.execute(new BioServerHandler(socket));
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

    public void start() {

    }

    public static void main(String[] args) {
        new BioServer();
    }
}
