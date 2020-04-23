package com.zhao.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

public class SimpleProduct {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("test");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.100.70:9876");
        //Launch the instance.
        producer.start();
        List<Message> list = new ArrayList<Message>();
        for (int i = 0; i < 3; i++) { //Tag0 Tag1 Tag2
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("test2" /* Topic */,
                    "Tag"+i /* Tag */,
                    ("testMessage" + i ).getBytes() /* Message body */
            );
            msg.putUserProperty("i",i+"");

            //一次发送1条 发送i次
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
//            list.add(msg);

            //批量发送不支持延迟 不支持过滤 不支持事务
            //设置延迟级别  不能直接设置延迟多久
//                msg.setDelayTimeLevel(3);
            //如果你要保证你有一系列消息需要有序的话 你只需要把他们这几条消息统一的标识 传递进来就可以了
            //orderid
            //                //Call send message to deliver message to one of brokers.
        }
        //发送一次消息 一次发送i(10)条
//            SendResult sendResult = producer.send(list);
//            System.out.printf("%s%n", sendResult);
        // 1.5MB   分2次发送  1次发送1MB 1次发送500Kb
//        ListSplitter listSplitter = new ListSplitter(list);
//        while (listSplitter.hasNext()) {
//            try {
//                List<Message> listItem = listSplitter.next();
//                producer.send(listItem);
//            } catch (Exception e) {
//                e.printStackTrace();
//                //handle the error
//            }
//        }
//            for (int i = 10; i < 20; i++) {
//                //Create a message instance, specifying topic, tag and message body.
//                Message msg = new Message("test2" /* Topic */,
//                        "TagA" /* Tag */,
//                        ("testMessage"+i).getBytes() /* Message body */
//                );
//                //如果你要保证你有一系列消息需要有序的话 你只需要把他们这几条消息统一的标识 传递进来就可以了
//                //orderid
//                //                //Call send message to deliver message to one of brokers.
//                SendResult sendResult = producer.send(msg, new SelectMessageQueueByHash(),"2");
//                System.out.printf("%s%n", sendResult);
//            }


        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
