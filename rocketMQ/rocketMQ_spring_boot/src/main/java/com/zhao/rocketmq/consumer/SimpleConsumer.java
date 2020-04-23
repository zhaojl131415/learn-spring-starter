package com.zhao.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class SimpleConsumer {


    public static void main(String[] args) throws InterruptedException, MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");

        // Specify name server addresses.
        consumer.setNamesrvAddr("192.168.100.70:9876");
        // Subscribe one more more topics to consume.
        //
        consumer.subscribe("test2", "*");
        // Register callback to execute on arrival of messages fetched from brokers.

        //单线程对应单队列
//        consumer.registerMessageListener(new MessageListenerOrderly() {
////            @Override
////            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
////                return null;
////            }
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
//                                                       ConsumeOrderlyContext context) {
//                for (MessageExt msg : msgs) {
//                    System.out.printf("%s Receive New Messages: %s %s 延迟时间 %s %n", Thread.currentThread().getName(), new String(msg.getBody()),msg.getQueueId(),(System.currentTimeMillis()-msg.getStoreTimestamp()));
//                }
//                return ConsumeOrderlyStatus.SUCCESS;
//            }
//        });

//Concurrently多线程无序消费
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.printf("%s Receive New Messages: %s %s Tag：%s %n", Thread.currentThread().getName(), new String(msg.getBody()),msg.getQueueId(),msg.getTags());
                }

                //ribbitMQ手动回调
//                ConsumeConcurrentlyStatus.
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
