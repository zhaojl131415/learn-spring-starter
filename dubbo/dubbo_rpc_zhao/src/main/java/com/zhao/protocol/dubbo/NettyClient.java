package com.zhao.protocol.dubbo;

import com.zhao.framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.*;

/**
 * @author zhao
 */
public class NettyClient<T> {

    private static NettyClientHandler client;

    private static ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public void start(String hostName, Integer port) {
        client = new NettyClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                        .addLast("encoder", new ObjectEncoder())
                        .addLast("handler", client);
                    }
                });
        try {
            b.connect(hostName, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String send(String hostName, Integer port, Invocation invocation) {
        if (client == null) {
            start(hostName, port);
        }
        client.setInvocation(invocation);

        try {
            return (String) executorService.submit(client).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
