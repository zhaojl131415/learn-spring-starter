package com.zhao.consumer;

import com.zhao.framework.Invocation;
import com.zhao.framework.Protocol;
import com.zhao.framework.ProtocolFactory;
import com.zhao.framework.URL;
import com.zhao.protocol.dubbo.NettyClient;
import com.zhao.protocol.dubbo.NettyProtocol;
import com.zhao.protocol.http.HttpClient;
import com.zhao.protocol.http.HttpProtocol;
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
//                    Protocol protocol = ProtocolFactory.getProtocol("netty");
//                    return protocol.send(url, invocation);

                    HttpClient httpClient = new HttpClient();
//                    NettyClient httpClient = new NettyClient<>();
                    return httpClient.send(url.getHostname(), url.getPort(), invocation);
                });
    }
}
