package com.zhao.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//聊天室服务端
public class ChatService {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private long timeout = 2000;


    public ChatService() {
        try {
            //服务端channel
            serverSocketChannel = ServerSocketChannel.open();

            //选择器对象
            selector = Selector.open();

            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9090));

            //设置连接非阻塞式: 必不可少的
            serverSocketChannel.configureBlocking(false);

            //把ServerSocketChannel注册给Selector
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//监听连接

            System.out.println("服务端准备就绪");

            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        // 用于存储空轮询次数
        int count = 0;
        long start = System.nanoTime();
        //干活
        while (true) {
            //监控客户端
//            if(selector.select(timeout)==0){
//                System.out.println("2秒内没有客户端来连接我");
//                continue;
//            }
            // 这里如果没有指定timeout, 会阻塞, 直到有连接才会继续执行
            // timeout: 2000, 表示两秒轮询一次
            // 这个方法有时候不会阻塞两秒, 有时候会失效,jdk中nio源码一个bug, netty也没有解决, 只是规避了这个bug
            selector.select(timeout);
//            System.out.println("2秒了");
            long end = System.nanoTime();
            // 如果发生了空轮询, 这里不成立, 走到else, 累加count
            if (end - start >= TimeUnit.MILLISECONDS.toNanos(timeout)) {
                count = 1;
            } else {
                count++;
            }

            // 空轮询, 重建Selector
            if (count >= 10) {
                System.out.println("有可能发生空轮询" + count + "次");
                rebuildSelector();
                count = 0;
                // 不会阻塞, 立马查看一次是否有事件发生
                selector.selectNow();
                continue;
            }
            // 得到SelectionKey对象集合, 表示有多少个事件来连接了，SelectionKey判断是什么事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //连接事件
                if (selectionKey.isAcceptable()) {
                    //获取网络通道
                    SocketChannel accept = serverSocketChannel.accept();
                    //设置读取数据非阻塞式
                    accept.configureBlocking(false);
                    //连接上了  注册读取事件
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println(accept.getRemoteAddress().toString() + "上线了");
                }
                //读取客户端数据事件
                if (selectionKey.isReadable()) {
                    //读取客户端发来的数据
                    readClientData(selectionKey);
                }
                // 手动从当前集合将本次运行完的对象删除
                iterator.remove();
            }
        }
    }

    // 重建Selector
    private void rebuildSelector() throws IOException {
        Selector newSelector = Selector.open();
        Selector oldSelect = selector;
        // 遍历所有的SelectionKey
        for (SelectionKey selectionKey : oldSelect.keys()) {
            // 获取附件
            Object att = selectionKey.attachment();
            // 获取SelectionKey的注册了哪些事件: OP_ACCEPT/OP_READ等
            int i = selectionKey.interestOps();
            // 取消
            selectionKey.cancel();
            // 向新的Selector注册对应的事件
            selectionKey.channel().register(newSelector, i, att);
        }
        selector = newSelector;
        oldSelect.close();
    }

    //读取客户端发来的数据
    private void readClientData(SelectionKey selectionKey) throws IOException {
//        System.out.println("aaaaaaaaaaaa");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(byteBuffer);
        byteBuffer.flip();
        if (read > 0) {
            byte[] bytes = new byte[read];
            byteBuffer.get(bytes, 0, read);
            //读取了数据  广播
            String s = new String(bytes, "utf-8");
            writeClientData(socketChannel, s);
        }
    }

    //广播  将读取的数据群发
    private void writeClientData(SocketChannel socketChannel, String s) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            if (key.isValid()) {
                SelectableChannel channel = key.channel();
                // 剔除服务器端channel
                if (channel instanceof SocketChannel) {
                    SocketChannel socketChannel1 = (SocketChannel) channel;
                    // 转发给除自己以外的客户端channel
                    if (channel != socketChannel) {
                        ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
                        socketChannel1.write(wrap);
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new ChatService();
    }


}
