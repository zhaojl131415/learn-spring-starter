//package com.zhao.rocketmq.consumer;
//
//import com.alibaba.fastjson.JSON;
//import com.zhao.rocketmq.entity.OrderPaidEvent;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * @author zhaojinliang
// * @version 1.0
// * @description TODO
// * @date 2020-04-23 23:32
// */
//@Service
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-group")
//public class OrderConsumer implements RocketMQListener<OrderPaidEvent> {
//
//    @Override
//    public void onMessage(OrderPaidEvent message) {
//
//        System.out.print("------- OrderPaidEventConsumer received:"+ JSON.toJSONString(message));
//    }
//}
