package com.zhao.rabbitmq.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
public class RabbitMQConfig {

    public static final String ZHAO_QUEUE = "zhao_queue";
    public static final String ZHAO_EXCHANGE = "zhao_exchange";
    public static final String ZHAO_EXCHANGE_BAK = "zhao_exchange_bak";
    public static final String ZHAO_ROUTINGKEY = "zhao_routingKey";

    /**
     * 队列
     */
    @Bean
    public Queue zhaoQueue() {
        // 是否持久化
        return new Queue(ZHAO_QUEUE, true);
    }

    /**
     * 交换机
     */
    @Bean
    public FanoutExchange zhaoExchangeBak() {
        return new FanoutExchange(ZHAO_EXCHANGE_BAK);
    }

    /**
     * 绑定
     */
    @Bean
    public Binding zhaoBindingBak() {
        return BindingBuilder.bind(zhaoQueue()).to(zhaoExchangeBak());
    }

    /**
     * 交换机
     */
    @Bean
    public DirectExchange zhaoExchange() {
//        return new DirectExchange(ZHAO_EXCHANGE);
        // 添加备用交换机
        Map<String, Object> map = new HashMap<>(2);
        map.put("alternate-exchange", ZHAO_EXCHANGE_BAK);
        return new DirectExchange(ZHAO_EXCHANGE,true, false, map);
    }

    /**
     * 绑定
     */
    @Bean
    public Binding zhaoBinding() {
        return BindingBuilder.bind(zhaoQueue()).to(zhaoExchange()).with(ZHAO_ROUTINGKEY);
    }

    @Bean
//    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 自定义消息转换器
        rabbitTemplate.setMessageConverter(new MessageConverter() {
            @Override
            public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
                messageProperties.setContentType("text/plain");
                messageProperties.setContentEncoding("UTF-8");
                Message message = new Message(JSON.toJSONBytes(o), messageProperties);
                System.out.println("调用了自定义消息转换器");
                return message;
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                return null;
            }
        });
//        // 指定确认回调接口的实现类
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            System.out.println("CallBackConfirm ID: " + correlationData.getId());
//            System.out.println(ack ? "CallBackConfirm成功！" : "CallBackConfirm失败！");
//            System.out.println("CallBackConfirm Cause: " + cause);
//        });
//
//        //开启mandatory模式（开启失败回调）
//        rabbitTemplate.setMandatory(true);
//        // 指定失败回调接口的实现类
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            System.out.println(message);
//            System.out.println(replyCode);
//            System.out.println(replyText);
//            System.out.println(exchange);
//            System.out.println(routingKey);
//        });
        return rabbitTemplate;
    }
}
