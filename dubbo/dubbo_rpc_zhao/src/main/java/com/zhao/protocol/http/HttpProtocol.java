package com.zhao.protocol.http;

import com.zhao.framework.Invocation;
import com.zhao.framework.Protocol;
import com.zhao.framework.URL;

public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        new HttpServer().start(url.getHostname(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new HttpClient().send(url.getHostname(), url.getPort(), invocation);
    }
}
