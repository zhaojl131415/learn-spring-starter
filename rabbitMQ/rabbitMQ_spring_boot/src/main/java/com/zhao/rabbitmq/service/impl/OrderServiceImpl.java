package com.zhao.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhao.rabbitmq.config.RabbitMQConfig;
import com.zhao.rabbitmq.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
//        // 指定确认回调接口的实现类
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            System.out.println("================================order================================");
//            System.out.println("CallBackConfirm ID: " + correlationData.getId());
//            System.out.println(ack ? "CallBackConfirm成功！": "CallBackConfirm失败！");
//            System.out.println("CallBackConfirm Cause: " + cause);
//            System.out.println("================================order================================");
//        });
//
//        //开启mandatory模式（开启失败回调）
////        rabbitTemplate.setMandatory(true);
//        // 指定失败回调接口的实现类
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            System.out.println("================================order================================");
//            System.out.println(message);
//            System.out.println(replyCode);
//            System.out.println(replyText);
//            System.out.println(exchange);
//            System.out.println(routingKey);
//            System.out.println("================================order================================");
//        });
    }

    @Override
    public void order(String msg) {
        init();
        CorrelationData correlationData = new CorrelationData("orderId");
        Map<String, String> map = new HashMap<>(2);
        map.put("name","zhao");
//        map.put("password", "leon");

//        for (int i = 0; i < 100; i++) {
//            map.put("msg", msg + i);
            map.put("msg", msg);
//            rabbitTemplate.convertAndSend(RabbitMQConfig.ZHAO_EXCHANGE, RabbitMQConfig.ZHAO_ROUTINGKEY, JSON.toJSONString(map), correlationData);
            rabbitTemplate.convertAndSend(RabbitMQConfig.ZHAO_NORMAL_EXCHANGE, RabbitMQConfig.ZHAO_NORMAL_ROUTINGKEY, JSON.toJSONString(map), correlationData);
//        }

    }
}
