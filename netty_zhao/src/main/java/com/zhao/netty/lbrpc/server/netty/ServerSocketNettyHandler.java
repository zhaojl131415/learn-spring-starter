package com.zhao.netty.lbrpc.server.netty;

import com.zhao.netty.lbrpc.entity.ClassInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class ServerSocketNettyHandler extends ChannelInboundHandlerAdapter {

    public static ServerSocketNettyHandler serverSocketNettyHandler = new ServerSocketNettyHandler();


    private static ExecutorService executorService = Executors.newFixedThreadPool(1000);

    /**
     * 得到某个接口下的实现类
     * @param classInfo 客户端调用的类信息
     * @return
     * @throws Exception
     */
    public String getImplClassName(ClassInfo classInfo) throws Exception {
        //服务器接口与实现类地址;
        String iName = "com.zhao.netty.lbrpc.server.service";
        // 获取客户端类信息的全限定名的最后一个点的位置 : com.zhao.netty.lbrpc.client.service.TestService
        int i = classInfo.getClassName().lastIndexOf(".");
        // 根据最后一个点的位置截取之后的文件名：.TestService
        String className = classInfo.getClassName().substring(i);
        // 服务端接口的路径 + 截取后的文件名 ： com.zhao.netty.lbrpc.server.service.TestService
        // 得到拼接后的全类名，加载类
        Class aClass = Class.forName(iName + className);
        // 反射
        Reflections reflections = new Reflections(iName);
        // 找实现类
        Set<Class<?>> classes = reflections.getSubTypesOf(aClass);
        if (classes.size() == 0) {
            System.out.println("未找到实现类");
            return null;
        } else if (classes.size() > 1) {
            System.out.println("找到多个实现类，未明确使用哪个实现类");
            return null;
        } else {
            Class[] classes1 = classes.toArray(new Class[0]);
            return classes1[0].getName();
        }
    }


    //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ClassInfo classInfo = (ClassInfo) msg;
                    // 根据类信息，找到在服务器端对应的实现类，并实例化
                    Object o = Class.forName(getImplClassName(classInfo)).newInstance();
                    // 反射调用实现类的方法
                    Method method = o.getClass().getMethod(classInfo.getMethodName(), classInfo.getClazzType());
                    Object invoke = method.invoke(o, classInfo.getArgs());
                    // 将返回值写回给客户端
                    ctx.channel().writeAndFlush(invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
