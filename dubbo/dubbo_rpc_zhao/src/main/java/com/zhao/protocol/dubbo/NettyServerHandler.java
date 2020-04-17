package com.zhao.protocol.dubbo;

import com.zhao.framework.Invocation;
import com.zhao.provider.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * @author zhao
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;

        System.out.println("server: msg==>"+msg);

        Class implClass = LocalRegister.get(invocation.getInterfaceName());

        Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        Object invoke = method.invoke(implClass.newInstance(), invocation.getParams());
        System.out.println("Netty===============" + invoke.toString());
        ctx.writeAndFlush("Netty: " + invoke);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
