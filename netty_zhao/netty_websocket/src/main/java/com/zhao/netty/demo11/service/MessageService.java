package com.zhao.netty.demo11.service;

import com.zhao.netty.demo11.dto.Response;
import com.zhao.netty.demo11.entity.Client;

public class MessageService {

    public static Response sendMessage(Client client, String message) {
        Response res = new Response();
        res.getData().put("id", client.getId());
        res.getData().put("message", message);
        res.getData().put("ts", System.currentTimeMillis());// 返回毫秒数
        return res;
    }
}
