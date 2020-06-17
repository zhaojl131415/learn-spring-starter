package com.zhao.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);  //接收客户端连接的线程组
        EventLoopGroup workGroup=new NioEventLoopGroup(); //真正处理读写事件的线程组，没有指定数量默认为(cpu核数 * 2)
        try {
            // 服务端的一个启动辅助类，通过给他设置一系列参数来绑定端口启动服务
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    //服务端用的通道
                    .channel(NioServerSocketChannel.class)
                    // 处理器： 服务端
                    // .handler()
                    //处理器： 已经连接上来的客户端
                    .childHandler(new TestServerInitializer());
            // 绑定端口

            // .sync() 这里添加sync的调用是为了阻塞，因为Future都是异步的了，如果不加sync()，就立即返回了，加sync就是为了阻塞，执行完再返回。
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
