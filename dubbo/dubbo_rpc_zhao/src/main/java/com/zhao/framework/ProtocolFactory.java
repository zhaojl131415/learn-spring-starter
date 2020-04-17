package com.zhao.framework;

import com.zhao.protocol.dubbo.NettyProtocol;
import com.zhao.protocol.http.HttpProtocol;

public class ProtocolFactory {
    public static Protocol getProtocol(String... protocolName){
        String name = "";
        if (protocolName.length < 1) {
            name = System.getProperty("protocolName", "http");
        } else if (protocolName[0] == null || "".equals(protocolName[0])) {
            name = "http";
        } else {
            name = protocolName[0];
        }

        switch (name) {
            case "http": return new HttpProtocol();
            case "netty": return new NettyProtocol();
            default:break;
        }
        return new HttpProtocol();
    }
}
