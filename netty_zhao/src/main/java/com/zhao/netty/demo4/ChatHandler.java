package com.zhao.netty.demo4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 自定义handler,继承简单频道入站处理程序,范围为wen文本套接字Frame
 * websocket间通过frame进行数据的传递和发送
 * 此版本为user与channel绑定的版本，消息会定向发送和接收到指定的user的channel中。
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    
    //定义channel集合,管理channel,传入全局事件执行器
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 定义信道的消息处理机制,该方法处理一次,故需要同时对所有客户端进行操作(channelGroup)
     * @param ctx 上下文
     * @param msg 文本消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //1. 获取客户端传递过来的消息,其对象为TextWebSocketFrame
        String text = msg.text();
//        System.out.println("接收到数据为: "+ text);
        /**
         * writeAndFlush接收的参数类型是Object类型，但是一般我们都是要传入管道中传输数据的类型，比如我们当前的demo
         * 传输的就是TextWebSocketFrame类型的数据
         */
//        ctx.channel().writeAndFlush(new TextWebSocketFrame(LocalDateTime.now() + "："+ text));

        // 发送数据的客户端
        Channel channel = ctx.channel();
        // 遍历客户端群组
        users.forEach(ch -> {
            // 给除自己以外的客户端发送数据
//            if (channel != ch) {
                ch.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress()+ "\n" + LocalDateTime.now() + "："+ text));
//            }
        });
    }

    /**
     * 当客户端连接服务端之后(打开连接)----->handlerAdded
     * 获取客户端的channel,并且放到ChannelGroup中去管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

//        System.out.println("客户端添加，channelId为：" +  channel.id().asShortText());
//        System.out.println(channel.remoteAddress() + "加入");
//        System.out.println("A");
        // 广播给群组内的所有客户端
        users.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress() + "加入直播间\n"));
        // 给所有客户端广播之后，再将自己加入群组，自己加入的通知，不用发给自己
        users.add(channel);
    }

    //处理器移除时,移除channelGroup中的channel
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //打印移除的channel
//        System.out.println("客户端被移除，channelId为：" + channel.id().asShortText());
        // 广播给群组内的所有客户端
        users.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress() + "退出直播间\n"));
        users.remove(ctx.channel());
    }

    //channel活跃 通道准备就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        users.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress() + "上线\n"));
//        System.out.println(channel.remoteAddress() + "上线");
//        System.out.println(users.size());
    }

    //channel不活跃  通道关闭事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        users.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress() + "下线\n"));
//        System.out.println(channel.remoteAddress() + "下线");
    }

//    //channel注册事件
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelRegistered");
//        super.channelRegistered(ctx);
//    }
//
//    //channel取消注册事件
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelUnregistered");
//        super.channelUnregistered(ctx);
//    }

    /**
     * 发生异常时，关闭连接（channel），随后将channel从ChannelGroup中移除
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出错啦, 原因是:"+cause.getMessage());
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
