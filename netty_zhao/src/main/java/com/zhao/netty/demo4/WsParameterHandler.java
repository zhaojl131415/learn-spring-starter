package com.zhao.netty.demo4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WsParameterHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest= (HttpRequest) msg;
            String uri = httpRequest.uri();
            System.out.println(uri);
            if (uri.contains("?")) {
                String parameter = uri.substring(uri.indexOf("?"));
                System.out.println(parameter);
                uri = uri.substring(0, uri.indexOf("?"));
                System.out.println(uri);
                httpRequest.setUri(uri);
                System.out.println(httpRequest.uri());
            }

            ctx.fireChannelRead(httpRequest);
        }
    }

}
