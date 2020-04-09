package com.zhao.netty.demo3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class TestServerHandler extends SimpleChannelInboundHandler<String> {

    // 专门用来存放客户端连接通道的集合
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 通道读取数据
     * 接收客户端发来的数据
     * @param channelHandlerContext 发送数据的客户端
     * @param s 客户端发送的数据，因为有StringDecoder处理器，会将byte[]转成String
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        // 发送数据的客户端
        Channel channel = channelHandlerContext.channel();
        // 遍历客户端群组
        group.forEach(ch -> {
            // 给除自己以外的客户端发送数据
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "：" + s + "\r\n");
            }
        });
        // 向下传递给之后的处理器
        channelHandlerContext.fireChannelRead(s);
    }

    /**
     * channel 助手类(拦截器)的添加
     * 有客户端连接会调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("A");
        // 广播给群组内的所有客户端
        group.writeAndFlush(channel.remoteAddress() + "加入\n");
        // 给所有客户端广播之后，再将自己加入群组，自己加入的通知，不用发给自己
        group.add(channel);
    }

    //channel 助手类(拦截器)移除
    // 不用手动从group移除，group有监听
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("B");
        group.writeAndFlush(channel.remoteAddress() + "离开\n");
    }

    //channel活跃 通道准备就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("a");
        System.out.println(channel.remoteAddress() + "上线");
        System.out.println(group.size());
    }

    //channel不活跃  通道关闭事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("b");
        System.out.println(channel.remoteAddress() + "下线");
    }

    //channel注册事件
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    //channel取消注册事件
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    //发生异常回调
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
