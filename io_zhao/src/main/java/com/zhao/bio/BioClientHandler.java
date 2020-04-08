package com.zhao.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 14:21
 */
public class BioClientHandler implements Runnable {

    // 负责跟客户端通信
    private Socket socket;

    public BioClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream is = null;

        try {
            is = socket.getInputStream();
            int count = 0;
            byte[] bytes = new byte[1024];
            while ((count = is.read(bytes)) != -1) {
                String line = new String(bytes, 0, count, "utf-8");
                System.out.println("收到服务端的消息:"+line);
                System.out.println("请输入需要发送的消息:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
