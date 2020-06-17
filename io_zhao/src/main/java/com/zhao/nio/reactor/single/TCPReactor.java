// Reactor線程
package com.zhao.nio.reactor.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        // 打开Selector
        selector = Selector.open();
        // 打开ServerSocketChannel
        ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        // 在ServerSocketChannel绑定监听端口
        ssc.socket().bind(addr);
        // 设置ServerSocketChannel 非阻塞
        ssc.configureBlocking(false);
        // ServerSocketChannel向selector注册一个OP_ACCEPT事件，然后返回该通道的key
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
        // 给定key一个附加的Acceptor对象
        sk.attach(new Acceptor(selector, ssc));
    }

    @Override
    public void run() {
        // 在线程被中断前持续运行
        while (!Thread.interrupted()) {
            System.out.println("Waiting for new event on port: " + ssc.socket().getLocalPort() + "...");
            try {
                // 查看是否有事件就绪, 若没有, 则不往下执行
                // 这里没有指定轮询超时时间, 会阻塞在这, 直到调用selector.wakeup();才会立即执行
                if (selector.select() == 0)
                    continue;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 取得所有已就绪事件的key集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                // 根据事件的key进行调度
                dispatch((it.next()));
                it.remove();
            }
        }
    }

    /**
     * 调度方法，事件key绑定的附加对象开新线程
     * @param key
     */
    private void dispatch(SelectionKey key) {
        // 根据事件key绑定的附加对象:
        // new Acceptor(selector, ssc) / new TCPHandler(sk, sc)
        // 这里没有开启新线程, 也还是普通的方法调用
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            /**
             * OP_ACCEPT: {@link Acceptor#run()}
             * OP_READ: {@link TCPHandler#run()}
             * OP_WRITE: {@link TCPHandler#run()}
             */
            r.run();
        }
    }

}