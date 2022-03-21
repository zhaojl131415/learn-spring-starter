package com.zhao.netty.demo_yuxin;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于处理客户端与服务端的心跳，在客户端空闲（如飞行模式)时关闭channel，节省服务器资源
 *
 */
public class HeartBeatHandler extends ChannelHandlerAdapter {

    /**
     * 用户事件触发的处理器
     * 依托于IdleStateHandler的自定义心跳处理器，如果服务器端超过IdleStateHandler中配置的三个时长，会触发这个方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断evt是否属于IdleStateEvent，用于触发用户事件，包含读空闲，写空闲，读写空闲
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event= (IdleStateEvent) evt;
            switch (event.state()){
                // IdleStateHandler处理器配置的readerIdleTimeSeconds时长超时，状态为READER_IDLE
                case READER_IDLE:
                    //读空闲，不做处理，如果只有读空闲，写不空闲，表示服务器端在往客户端写数据，不能关闭通道
                    System.out.println(ctx.channel().remoteAddress()+"----读空闲");
                    break;
                // IdleStateHandler处理器配置的writerIdleTimeSeconds时长超时，状态为WRITER_IDLE
                case WRITER_IDLE:
                    //写空闲，不做处理，如果只有写空闲，读不空闲，表示服务器端在读客户端的数据，不能关闭通道
                    System.out.println(ctx.channel().remoteAddress()+"----写空闲");
                    break;
                // IdleStateHandler处理器配置的allIdleTimeSeconds时长超时，状态为ALL_IDLE
                case ALL_IDLE:
                    // 只有读写都空闲，才能关闭通道
                    System.out.println(ctx.channel().remoteAddress()+"----读写空闲");
                    // 关闭channel
                    ctx.channel().close();
                    break;
            }
        }
    }
}
