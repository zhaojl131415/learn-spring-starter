package com.zhao.rabbitmq.consumer;

import com.zhao.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
    public void get(Message message) throws Exception {
        System.out.println("消费者1：" + new String(message.getBody(), "UTF-8"));
    }

    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
    public void get(String message) throws Exception {
        System.out.println("消费者2：" + message);
    }
}
