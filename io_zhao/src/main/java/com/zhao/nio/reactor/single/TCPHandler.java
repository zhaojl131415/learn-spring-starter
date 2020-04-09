// Handler線程
package com.zhao.nio.reactor.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPHandler implements Runnable {

    private final SelectionKey sk;
    private final SocketChannel sc;
    // 0:读 1:写
    int state;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        this.sk = sk;
        this.sc = sc;
        // 初始状态设置为 READING
        state = 0;
    }

    @Override
    public void run() {
        try {
            if (state == 0){
                // 读取网络数据, 并修改state = 1
                read();
            } else {
                // 发送网络数据, 并修改state = 0
                send();
            }
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    private void closeChannel() {
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private synchronized void read() throws IOException {
        // non-blocking下不可用Readers，因为Readers不支援non-blocking
        // 创建一个ByteBuffer
        byte[] arr = new byte[1024];
        ByteBuffer buf = ByteBuffer.wrap(arr);

        // 读取字符串, 将从SocketChannel内读取到的数据写入ByteBuffer
        int numBytes = sc.read(buf);
        if (numBytes == -1) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            return;
        }
        // 将读取到的byte内容转为字符串形态
        String str = new String(arr);
        if ((str != null) && !str.equals(" ")) {
            // 逻辑处理
            process(str);
            System.out.println(sc.socket().getRemoteSocketAddress().toString()
                    + " > " + str);
            // 改变状态
            state = 1;
            // 通过key改变通道注册的事件为OP_WRITE
            sk.interestOps(SelectionKey.OP_WRITE);
            // 使一个阻塞住的selector操作立即返回
            sk.selector().wakeup();
        }
    }

    private void send() throws IOException {
        // get message from message queue

        String str = "Your message has sent to "
                + sc.socket().getLocalSocketAddress().toString() + "\r\n";
        // wrap自动把buf的position设为0，所以不需要再flip()
        ByteBuffer buf = ByteBuffer.wrap(str.getBytes());

        while (buf.hasRemaining()) {
            // 回传给client回应字符串，发送buf的position位置 到limit位置为止之间的内容
            sc.write(buf);
        }
        // 改变状态
        state = 0;
        // 通过key改变通道注册的事件为OP_READ
        sk.interestOps(SelectionKey.OP_READ);
        // 使一个阻塞住的selector操作立即返回
        sk.selector().wakeup();
    }

    void process(String str) {
        // do process(decode, logically process, encode)..
        // ..
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}