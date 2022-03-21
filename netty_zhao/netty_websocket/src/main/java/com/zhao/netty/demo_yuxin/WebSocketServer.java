package com.zhao.netty.demo_yuxin;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class WebSocketServer {
    public static void main(String[] args) throws Exception {

        // Reactor模型

        // Reactor主线程: 用于接收客户端连接事件的线程组 线程数:1
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // Reactor工作线程: 用于处理客户端读写事件的线程组 线程数未指定默认为: cpu核数 * 2
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.WARN))
                    .childHandler(new WebSocketServerInitializer());

            Channel ch = b.bind(8083).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
