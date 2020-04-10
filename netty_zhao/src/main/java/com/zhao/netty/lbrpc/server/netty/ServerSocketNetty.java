package com.zhao.netty.lbrpc.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ServerSocketNetty {
    public static void main(String[] args) throws InterruptedException {
        //创建一个线程组:接受客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建一个线程组:接受网络操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务器启动助手来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)   //设置使用NioServerSocketChannel作为服务器通道的实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列中等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {    //创建一个初始化管道对象
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /**
                             * 这两步其实就是Java的序列化和反序列化
                             * 序列化：  指把Java对象转换为字节序列的过程
                             * 反序列化：指把字节序列恢复为Java对象的过程
                             */
                            //添加编码器 出栈：序列化
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //添加解码器 入栈：反序列化
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //将自己编写的服务器端的业务逻辑处理类加入pipeline链中
                            pipeline.addLast(ServerSocketNettyHandler.serverSocketNettyHandler);
                        }
                    });
            System.out.println(".........server  init..........");
            ChannelFuture future = serverBootstrap.bind(9090).sync();//设置端口  非阻塞
            System.out.println(".........server start..........");
            //关闭通道  关闭线程组  非阻塞
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
