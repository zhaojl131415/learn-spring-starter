package com.zhao.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zhao.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // =============================================================================================================
//    /**
//     * 手动确认ack
//     */
//    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
//    public void get(Message message, Channel channel) throws Exception {
////        System.out.println("消费者1：" + new String(message.getBody(), "UTF-8"));
//        log.info("消费者A收到消息:{}", message.getPayload());
//        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        /**
//         * 消费如果抛出了异常，处理方式:
//         * 如果是手动签收模式，可以try catch包括，catch到了异常进行重回队列或者进行落库等操作
//         * 如果是自动签收，默认会重回队列，然后一直循环重复消费。可以设置消息重新投递(设置最大投递次数，投递时间间隔，达到最大投递次数后是否重回队列等)
//         */
//        //模拟消费时抛出异常
//        int i=1/0;
//        //手工ACK
//        channel.basicAck(deliveryTag, false);
//    }
//
//    // =============================================================================================================
//    /**
//     * 消息消费失败, ack, 退回消息队列
//     */
//    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
//    public void get1(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
//        try {
//            log.info("消费者B收到消息:{}", new String(message.getBody(), "UTF-8"));
//            //模拟消费时抛出异常
//            int i=1/0;
//            //手工ACK
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            // 消费报错
//            // 第一个false表示不是批量, 第二个false表示退回原消息队列
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            // 单条退回
////            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//        }
//    }
//
//    // =============================================================================================================
//    /**
//     * 两个消费端, 性能不同, 测试消息预取, 配合prefetch: 10使用, 每次预取10条
//     */
//    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
//    public void get2(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
//        log.info("消费者B收到消息:{}", new String(message.getBody(), "UTF-8"));
//        //模拟消费时抛出异常
////        int i=1/0;
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //手工ACK
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//    }
//
//    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
//    public void get3(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
//        log.info("消费者C收到消息:{}", new String(message.getBody(), "UTF-8"));
//        //模拟消费时抛出异常
////        int i=1/0;
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //手工ACK
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//    }

    // =============================================================================================================
    /**
     * 普通队列 消费消息失败 转发给死信交换机
     */
    @RabbitListener(queues = RabbitMQConfig.ZHAO_NORMAL_QUEUE)
    public void getNormal(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("消费者A收到正常消息:{}", new String(message.getBody(), "UTF-8"));
            //模拟消费时抛出异常
            int i=1/0;
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消费报错
            // 第一个false表示不是批量, 第二个false表示不退回原消息队列
            log.info("消费者A消费消息失败");
            channel.basicNack(deliveryTag, false, false);
        }
    }


    /**
     * 死信队列
     */
    @RabbitListener(queues = RabbitMQConfig.ZHAO_DELAY_QUEUE)
    public void getDelay(Message message, Channel channel) throws Exception {
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        log.info("消费者A收到死信消息:{}", message.getPayload());
        channel.basicAck(deliveryTag, false);
    }



    // =============================================================================================================
//    /**
//     * 获取重试次数
//     *
//     * 测试不成功
//     *
//     * @param message
//     * @param channel
//     * @throws Exception
//     */
//    @RabbitListener(queues = RabbitMQConfig.ZHAO_NORMAL_QUEUE)
//    public void getNormalRetry(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
//        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        try {
//            log.info("消费者A收到正常消息:{}", new String(message.getBody(), "UTF-8"));
//            //模拟消费时抛出异常
//            int i=1/0;
//        } catch (Exception e) {
//            // 消费报错
//            // 第一个false表示不是批量, 第二个false表示不退回原消息队列
//            log.info("消费者A消费消息失败");
//            channel.basicNack(deliveryTag, false, false);
//            long retryCount = getRetryCount(message.getMessageProperties());
////            long retryCount = 0L;
//            //判断失败次数
//            if(retryCount>=3){
//                //如果失败超过三次，则发送到失败队列
////                channel.basicNack(deliveryTag, false, false);
//                rabbitTemplate.convertAndSend(RabbitMQConfig.ZHAO_DELAY_EXCHANGE, RabbitMQConfig.ZHAO_DELAY_ROUTINGKEY, message);
//            }else{
//                //发送到重试队列,10s后重试
////                channel.basicNack(deliveryTag, false, true);
//                rabbitTemplate.convertAndSend(RabbitMQConfig.ZHAO_NORMAL_EXCHANGE, RabbitMQConfig.ZHAO_NORMAL_ROUTINGKEY, message);
//            }
//        } finally {
//            channel.basicAck(deliveryTag, false);
//        }
//    }
//
//    /**
//     * 获取消息被重试的次数
//     */
//    public long getRetryCount(MessageProperties messageProperties) {
//        Long retryCount = 0L;
//        if (null != messageProperties) {
//            List<Map<String, ?>> deaths = messageProperties.getXDeathHeader();
//            if(deaths != null && deaths.size()>0){
//                Map<String, Object> death = (Map<String, Object>)deaths.get(0);
//                retryCount = (Long) death.get("count");
//            }
//        }
//        return retryCount;
//    }

    // =============================================================================================================

//    @RabbitListener(queues = RabbitMQConfig.ZHAO_QUEUE)
//    public void get(String message) throws Exception {
//        System.out.println("消费者2：" + message);
//    }
}
