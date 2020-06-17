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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
public class RabbitMQConfig {
    // 正常队列
    public static final String ZHAO_QUEUE = "zhao_queue";
    public static final String ZHAO_EXCHANGE = "zhao_exchange";
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
    public DirectExchange zhaoExchange() {
        return new DirectExchange(ZHAO_EXCHANGE);
        // 添加备用交换机
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("alternate-exchange", ZHAO_EXCHANGE_BAK);
//        return new DirectExchange(ZHAO_EXCHANGE,true, false, map);
    }

    /**
     * 绑定
     */
    @Bean
    public Binding zhaoBinding() {
        return BindingBuilder.bind(zhaoQueue()).to(zhaoExchange()).with(ZHAO_ROUTINGKEY);
    }




    // 死信队列交换器
    public static final String ZHAO_NORMAL_QUEUE = "zhao_normal_queue";
    public static final String ZHAO_DELAY_QUEUE = "zhao_delay_queue";
    public static final String ZHAO_NORMAL_EXCHANGE = "zhao_normal_exchange";
    public static final String ZHAO_DELAY_EXCHANGE = "zhao_delay_exchange";
    public static final String ZHAO_NORMAL_ROUTINGKEY = "zhao_normal_routingKey";
    public static final String ZHAO_DELAY_ROUTINGKEY = "zhao_delay_routingKey";

    /**
     * 正常队列 附带 死信交换机
     */
    @Bean
    public Queue zhaoNormalQueue() {
//        return QueueBuilder.durable(ZHAO_NORMAL_QUEUE)
//                .withArgument("x-message-ttl", 10000) //设置消息的过期时间 单位毫秒
//                .withArgument("x-dead-letter-exchange", ZHAO_DELAY_EXCHANGE) //设置附带的死信交换机
//                .withArgument("x-dead-letter-routing-key", ZHAO_DELAY_ROUTINGKEY)
//                .build();

        Map<String,Object> map = new HashMap<>();
        //设置消息的过期时间 单位毫秒
        map.put("x-message-ttl",10000);
        //设置附带的死信交换机
        map.put("x-dead-letter-exchange", ZHAO_DELAY_EXCHANGE);
        map.put("x-dead-letter-routing-key", ZHAO_DELAY_ROUTINGKEY);
        //指定重定向的路由建 消息作废之后可以决定需不需要更改他的路由建 如果需要 就在这里指定 map.put("x-dead-letter-routing-key", ZHAO_DELAY_ROUTINGKEY);
        return new Queue(ZHAO_NORMAL_QUEUE, true,false,false, map);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue zhaoDelayQueue() {
        return new Queue(ZHAO_DELAY_QUEUE, true);
    }

    /**
     * 正常交换机
     */
    @Bean
    public DirectExchange zhaoNormalExchange() {
        return new DirectExchange(ZHAO_NORMAL_EXCHANGE);
    }

    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange zhaoDelayExchange() {
        return new DirectExchange(ZHAO_DELAY_EXCHANGE);
    }

    /**
     * 正常队列 绑定 正常交换机
     */
    @Bean
    public Binding zhaoNormalBinding() {
        return BindingBuilder.bind(zhaoNormalQueue()).to(zhaoNormalExchange()).with(ZHAO_NORMAL_ROUTINGKEY);
    }

    /**
     * 死信队列 绑定 死信交换机
     */
    @Bean
    public Binding zhaoDelayBinding() {
        return BindingBuilder.bind(zhaoDelayQueue()).to(zhaoDelayExchange()).with(ZHAO_DELAY_ROUTINGKEY);
    }




//    @Bean
////    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        // 消息转换器
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        // 自定义消息转换器
//        rabbitTemplate.setMessageConverter(new MessageConverter() {
//            @Override
//            public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
//                messageProperties.setContentType("text/plain");
//                messageProperties.setContentEncoding("UTF-8");
//                Message message = new Message(JSON.toJSONBytes(o), messageProperties);
//                System.out.println("调用了自定义消息转换器");
//                return message;
//            }
//
//            @Override
//            public Object fromMessage(Message message) throws MessageConversionException {
//                return null;
//            }
//        });
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
//        return rabbitTemplate;
//    }
}
