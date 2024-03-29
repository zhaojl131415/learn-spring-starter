package com.zhao.netty.yace;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-09-08 21:32
 */
public class Client {
    //服务端的IP
    private static final String SERVER_HOST = "https://netty.yunduoketang.com";

    static final int BEGIN_PORT = 10000;
    static final int N_PORT = 100;

    public static void main(String[] args) {
        new Client().start(BEGIN_PORT, N_PORT);
    }

    public void start(final int beginPort, int nPort) {
        System.out.println("client starting....");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
            }
        });

        int index = 0;
        int port;
        //从10000的端口开始，按端口递增的方式进行连接
        while (!Thread.interrupted()) {
            port = beginPort + index;
            try {
                ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, port);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        System.out.println("connect failed, exit!");
                        System.exit(0);
                    }
                });
                channelFuture.get();
            } catch (Exception e) {
            }

            if (++index == nPort) {
                index = 0;
            }
        }
    }
}
