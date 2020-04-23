package com.zhao.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;


public class TranstionProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("192.168.100.70:9876");
        producer.setTransactionListener(transactionListener);
        producer.start();
        //t1提交 t2回滚 其他挂起
        String[] tags = new String[] {"t1", "t2", "t3"};
        for (int i = 0; i < 3; i++) {
            try {
                Message msg =
                        new Message("test2", tags[i],
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


//        for (int i = 0; i < 100000; i++) {
//            Thread.sleep(1000);
//        }
//        producer.shutdown();
    }

}
