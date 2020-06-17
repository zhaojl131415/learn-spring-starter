package com.spring.cloud.alibaba.rocketmq.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-02-09 21:08
 */
@Service
public class ProviderService {
    @Autowired
    private MessageChannel output;

    public void send(String message) {
        output.send(MessageBuilder.withPayload(message).build());
    }
}