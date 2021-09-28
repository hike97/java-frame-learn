package com.atguigu.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hike97
 * @create 2021-08-23 20:46
 * @desc 分布式事务
 **/
public class TransactionProducer {
    public static void main (String[] args) throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer ("xa-demo");
        producer.setNamesrvAddr ("192.168.3.10:9876");
        //添加线程池
        producer.setExecutorService (new ThreadPoolExecutor (2, 5, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<> (1024), new ThreadFactory () {
            @Override
            public Thread newThread (Runnable r) {
                return new Thread (r, "client-transaction-msg-check-thread");
            }
        }));
        //TODO 为生产者添加事务监听器
        producer.setTransactionListener (new ICBCTransactionListener ());
        producer.start ();
        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 0; i < 3; i++) {
            byte[] body = ("Hi," + i).getBytes ();
            Message msg = new Message ("TTopic", tags[i], body);
            // 发送事务消息
            // 第二个参数用于指定在执行本地事务时要使用的业务参数
            // TM开启全局事务
            SendResult sendResult = producer.sendMessageInTransaction (msg, null);
            System.out.println ("发送结果为：" + sendResult.getSendStatus ());
        }
    }
}
