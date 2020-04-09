// 接受連線請求線程
package com.zhao.nio.reactor.single;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel ssc) {
        this.ssc = ssc;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            // 接受client连线请求
            SocketChannel sc = ssc.accept();
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");

            if (sc != null) {
                // 设置SocketChannel为非阻塞
                sc.configureBlocking(false);
                // SocketChannel向selector注册一个OP_READ事件，然后返回该通道的key
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                // 使一个阻塞住的selector操作, 立即返回
                selector.wakeup();
                // 给定key一个附加对象: TCPHandler
                sk.attach(new TCPHandler(sk, sc));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}