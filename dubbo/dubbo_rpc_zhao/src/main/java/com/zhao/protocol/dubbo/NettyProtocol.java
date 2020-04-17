package com.zhao.protocol.dubbo;

import com.zhao.framework.Invocation;
import com.zhao.framework.Protocol;
import com.zhao.framework.URL;

public class NettyProtocol implements Protocol {
    @Override
    public void start(URL url) {
        new NettyServer().start(url.getHostname(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new NettyClient<>().send(url.getHostname(), url.getPort(), invocation);
    }
}
