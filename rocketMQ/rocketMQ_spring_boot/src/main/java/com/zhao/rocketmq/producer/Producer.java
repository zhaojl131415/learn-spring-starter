package com.zhao.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-24 17:11
 */
@Component
public class Producer {

    /**
     * 生产者的组名
     */
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    /**
     * NameServer 地址
     */
    @Value("${rocketmq.nameServer}")
    private String namesrvAddr;

//    @PostConstruct
    public void defaultMQProducer() {


        //生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);

        producer.setVipChannelEnabled(false);
        producer.setSendMsgTimeout(10000);

        try {

            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();

            for (int i = 0; i < 100; i++) {

                String messageBody = "我是消息内容:" + i;

                String message = new String(messageBody.getBytes(), "utf-8");

                //构建消息
                Message msg = new Message("PushTopic" /* PushTopic */, "push"/* Tag  */, "key_" + i /* Keys */, message.getBytes());



                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        //
                    }

                    @Override
                    public void onException(Throwable e) {
                        e.printStackTrace();
                    }
                }, 20 * 1000);
//                //发送消息
//                SendResult result = producer.send(msg, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        //
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//                        e.printStackTrace();
//                    }
//                }, 20 * 1000);

//                System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }

        try {
            producer.start();
            System.out.println("-------->:producer启动了");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}
