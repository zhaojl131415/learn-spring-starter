package com.zhao.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description 客户端, 可以同时运行多个
 * @date 2020-04-04 14:15
 */
public class BioClient {

    public static void main(String[] args) {
        Socket socket = null;

        OutputStream os = null;

        try {
            socket = new Socket("localhost", 9090);

            /**
             * 开线程, 循环读
             * @see BioClientHandler#run()
             */
            new Thread(new BioClientHandler(socket)).start();
            os = socket.getOutputStream();

            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入需要发送的消息:");
            while (true) {
                String s = scanner.nextLine();
                if (s.trim().equals("bye")) {
                    break;
                }
                //
                os.write(s.getBytes());
                //
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
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
