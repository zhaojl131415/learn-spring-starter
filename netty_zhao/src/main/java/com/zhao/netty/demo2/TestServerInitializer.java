package com.zhao.netty.demo2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;



public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * LengthFieldBasedFrameDecoder、LengthFieldPrepender 解决java网络编程tcp协议粘包拆包问题：
         * https://www.cnblogs.com/f-zhao/p/7502075.html
         * https://cloud.tencent.com/developer/article/1486053
         *
         * 1) lengthFieldOffset  //长度字段的偏差
         * 2) lengthFieldLength  //长度字段占的字节数
         * 3) lengthAdjustment  //添加到长度字段的补偿值
         * 4) initialBytesToStrip  //从解码帧中第一次去除的字节数
         *
         * 出现粘包问题，可能读取的数据格式为：数据长度+数据内容+数据长度+数据内容...
         * 示例：24来自客户端的问候24来自客户端的问候24来自客户端的问候24来自客户
         * 根据LengthFieldBasedFrameDecoder处理器处理：
         * 其中第二个参数表示为数据长度所占的字节数：4，通过这个字节数我们能获取到示例中数据长度为24，通过这个24去截取数据内容：24来自客户端的问候，为一条数据
         * 其中第四个参数表示截取后需要去除的字节数：4，也就是截掉示例中的24，得到：来自客户端的问候
         * 最后一条数据长度不够，会被存储起来，等待下一次客户端发送的数据内容拼接，再处理
         *
         */
        // 1、ChannelInboundHandlerAdapter
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        // 2、ChannelOutboundHandlerAdapter
        pipeline.addLast(new LengthFieldPrepender(4)); //计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头中
        // 3、ChannelInboundHandlerAdapter：把从客户端接收来的byte[]转为String
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // 4、ChannelOutboundHandlerAdapter：把String转为byte[]发送给客户端
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // 5、SimpleChannelInboundHandler
        pipeline.addLast(new TestServerHandler());

        // 入栈执行顺序 head 1 3 5
        // 出栈执行顺序 tail 4 2


    }
}
