package com.zhao.rocketmq;

import com.zhao.rocketmq.entity.OrderPaidEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@SpringBootApplication
public class RocketMQSpringBootApplication {


    public static void main(String[] args) {
        SpringApplication.run(RocketMQSpringBootApplication.class, args);
    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        //send message synchronously
//        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
//        //send spring message
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
//        //send messgae asynchronously
//        rocketMQTemplate.asyncSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult var1) {
//                System.out.printf("async onSucess SendResult=%s %n", var1);
//            }
//
//            @Override
//            public void onException(Throwable var1) {
//                System.out.printf("async onException Throwable=%s %n", var1);
//            }
//
//        });
//        //Send messages orderly
//        rocketMQTemplate.syncSendOrderly("orderly_topic",MessageBuilder.withPayload("Hello, World").build(),"hashkey");
//
//        //rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
//    }
}
