package com.zhao.netty.demo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClient {
    public static void main(String[] args) {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TestClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8989).sync();
            Channel channel = channelFuture.channel();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            // 死循环给服务端发送数据
            for(; ;){
                channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
        }
    }
}
