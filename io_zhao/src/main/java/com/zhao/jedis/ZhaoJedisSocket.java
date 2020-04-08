package com.zhao.jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 15:07
 */
public class ZhaoJedisSocket {

    private String ip;
    private int port;

    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public ZhaoJedisSocket(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向redis服务器端发送命令
     * @param command
     */
    public void send(String command) {
        try {
            os.write(command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        byte[] bytes = new byte[1024];
        int count = 0;
        try {
            count = is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return new String(bytes, 0, count);
        }
    }

}
