package com.zhao.netty.demo1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * HttpRequestEncoder，将HttpRequest或HttpContent编码成ByteBuf
   HttpRequestDecoder，将ByteBuf解码成HttpRequest和HttpContent
   HttpResponseEncoder，将HttpResponse或HttpContent编码成ByteBuf
   HttpResponseDecoder，将ByteBuf解码成HttpResponse和HttpContent
 */
/**
 * ChannelInitializer   特殊的Handler：辅助添加Handler
 * 辅助添加自定义的Handler，服务启动后调用initChannel方法，把自定义的handler添加到pipeline里，然后从pipiline中移除自己
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 初始化ChannelPipeline，自定义的handler添加到pipeline
     * @param socketChannel 客户端的SocketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * 解析http协议的处理器，
         * HttpServerCodec extends CombinedChannelDuplexHandler,
         * 而这个CombinedChannelDuplexHandler既是入栈处理器也是出栈处理器
         */
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //
        pipeline.addLast("testServerHandler",new TestServerHandler());

        /**
         * ChannelInboundHandler 入栈处理器：服务端从客户端读数据，对于服务端而言就是入栈
         * ChannelOutboundHandler出栈处理器：服务端向客户端写数据，对于服务端而言就是出栈
         */
    }
}
