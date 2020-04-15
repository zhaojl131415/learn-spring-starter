package com.zhao.netty.rpc_zhao.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientSocketNettyHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    public Object getResponse(){
        return response;
    }

    //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.response=msg;
        ctx.close();
    }
}
