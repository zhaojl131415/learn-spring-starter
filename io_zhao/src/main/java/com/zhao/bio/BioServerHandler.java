package com.zhao.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 10:53
 */
public class BioServerHandler implements Runnable {
    // 负责跟客户端通信
    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            int count = 0;
            String content = null;
            byte[] bytes = new byte[1024];
            // read也是阻塞的, 如果客户端没有发来数据, 会一直阻塞在这
            while ((count = is.read(bytes)) != -1) {
                String line = new String(bytes, 0, count, "utf-8");
                System.out.println("收到客户端" + socket.getRemoteSocketAddress().toString() + "的消息:"+line);

                content = line.trim().equalsIgnoreCase("SJ") ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : "啥啥啥, 你这说的都是啥啊?";
                // 往客户端写数据
                os.write(content.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
