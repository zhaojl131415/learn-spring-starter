package com.zhao.rocketmq.controller;

import com.zhao.rocketmq.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-24 17:14
 */
@RestController
public class TestController {
    @Autowired
    private Producer producer;

    @RequestMapping("/push")
    public String pushMsg(String msg){
        try {
//            return producer.send("PushTopic","push",msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
