package com.site.trasactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hike97
 * @create 2021-07-10 9:47
 * @desc 事务消息模拟下单流程 consumer 可以用简单的consumer
 **/
public class TransactionalProducer {

    static final String NAMESRVADDR = "192.168.3.10:9876";

    private static AtomicInteger transactionIndex = new AtomicInteger (1);

    public static void main (String[] args) throws MQClientException, InterruptedException {

        TransactionMQProducer producer = new TransactionMQProducer ("trs-produce");
        //自定义线程池
        ExecutorService executorService = new ThreadPoolExecutor (2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable> (2000), new ThreadFactory () {
            @Override
            public Thread newThread (Runnable r) {
                Thread thread = new Thread (r);
                thread.setName ("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setNamesrvAddr (NAMESRVADDR);
        producer.setExecutorService (executorService);
        /**
         *  事务逻辑
         */
        producer.setTransactionListener (new TransactionListener () {
            //本地事务逻辑 提交MYSQL
            @Override
            public LocalTransactionState executeLocalTransaction (Message msg, Object arg) {
                String tags = msg.getTags ();
                if (StringUtils.contains (tags, "TagA")) {
                    System.out.println ("下单成功本地mysqlExec()");
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.contains (tags, "TagB")) {
                    System.out.println ("正在进行支付");
                    return LocalTransactionState.UNKNOW;
                } else {
                    //其他消息回滚
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            //事务监听
            @Override
            public LocalTransactionState checkLocalTransaction (MessageExt msg) {
                //模拟监听 3次后 tagB转为 commit 发送消息
                int value = transactionIndex.getAndIncrement ();
                System.out.println ("第" + value + "次监听");
                //                if (value==3){
                String tags = msg.getTags ();
                if (StringUtils.contains (tags, "TagB")) {
                    System.out.println ("支付成功准备发消息");
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                //                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
        producer.start ();
        //假设 A为下单通知 B为支付消息
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message ("TopicTest1234", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes (RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction (msg, null);
                System.out.printf ("%s%n", sendResult);

                Thread.sleep (10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace ();
            }
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep (1000);
        }
        producer.shutdown ();
    }
}
