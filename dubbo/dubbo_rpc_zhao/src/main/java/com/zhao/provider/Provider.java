package com.zhao.provider;

import com.zhao.framework.Protocol;
import com.zhao.framework.ProtocolFactory;
import com.zhao.framework.URL;
import com.zhao.protocol.dubbo.NettyProtocol;
import com.zhao.protocol.dubbo.NettyServer;
import com.zhao.protocol.http.HttpProtocol;
import com.zhao.protocol.http.HttpServer;
import com.zhao.provider.api.HelloService;
import com.zhao.provider.api.impl.HelloServiceImpl;
import com.zhao.register.RemoteRegister;

/**
 * @author zhao
 */
public class Provider {

    public static void main(String[] args) {
        //1.本地注册
        //{服务名：实现类}
//        System.out.println(HelloService.class.getName()+"========"+ HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

        //2.远程注册
        //{服务名：List(url)}
        URL url = new URL("127.0.0.1", 8080);
        RemoteRegister.register(HelloService.class.getName(), url);

        //3.启动
//        Protocol protocol = new HttpProtocol();
//        Protocol protocol = new NettyProtocol();
//      Protocol protocol = ProtocolFactory.getProtocol();
        Protocol protocol = ProtocolFactory.getProtocol("netty");
        protocol.start(url);

//        HttpServer server = new HttpServer();
//        NettyServer server = new NettyServer();
//        server.start("127.0.0.1", 8080);



    }
}
