package com.zhao.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description 服务器端
 * @date 2020-04-04 10:42
 */
public class BioServer {

    // 服务端socket
    ServerSocket serverSocket;

    ServerHandlerExecutorPool executorPool;

    public BioServer() {
        try {
            serverSocket = new ServerSocket(9090);
            executorPool = new ServerHandlerExecutorPool(10, 100);
            // 为了保证让main方法不会执行完: 长连接
            while(true) {
                // 客户端socket: 这一步是阻塞的, 没有获取到客户端, 会一直阻塞在这
                Socket socket = serverSocket.accept();

                System.out.println("客户端:" + socket.getRemoteSocketAddress().toString() + "来了...");

//                new Thread(new BioServerHandler(socket)).start();

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
