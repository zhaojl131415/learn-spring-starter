package com.zhao.framework;

import com.zhao.protocol.dubbo.NettyClient;
import com.zhao.register.RemoteRegister;

import java.lang.reflect.Proxy;

/**
 * @author zhao
 */
public class ProxyFactory {

    public static <T> T getProxy(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Invocation invocation = new Invocation(interfaceClass.getName(),
                            method.getName(),
                            method.getParameterTypes(),
                            args);
                    URL url = RemoteRegister.getRandom(interfaceClass.getName());

//                    Protocol protocol = new HttpProtocol();
//                    Protocol protocol = new NettyProtocol();
//                    Protocol protocol = ProtocolFactory.getProtocol();
                    Protocol protocol = ProtocolFactory.getProtocol("netty");
                    return protocol.send(url, invocation);

//                    HttpClient client = new HttpClient();
//                    NettyClient client = new NettyClient<>();
//                    return client.send(url.getHostname(), url.getPort(), invocation);
                });
    }
}
