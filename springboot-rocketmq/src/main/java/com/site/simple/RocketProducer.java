package com.site.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author hike97
 * rocketmq 的三种发送方式
 * reliable synchronous, 可靠同步
 * reliable asynchronous,可靠异步
 * and one-way transmission.单向传输
 */
public class RocketProducer {
    static final String NAMESRVADDR = "192.168.3.10:9876";

    /**
     * 1.Send Messages Synchronously 同步成功一个返回一个
     * 可靠的同步传输应用于广泛的场景，如重要通知消息、短信通知、短信营销系统等。
     */
    public static void main (String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer ("reliable-sync");
        // Specify name server addresses.
        producer.setNamesrvAddr (NAMESRVADDR);
        //Launch the instance.
        producer.start ();
        for (int i = 0; i < 2; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message ("Jodie_topic_1023" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes (RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send (msg);
            System.out.printf ("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown ();
    }

    /**
     * 2.Send Messages Asynchronously 成功一个调用一次回调函数
     * 异步传输一般用于响应时间敏感的业务场景。
     */
    @Test
    public void testAsyncProduce () throws MQClientException, InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer ("reliable-async");
        // Specify name server addresses.
        producer.setNamesrvAddr (NAMESRVADDR);
        //Launch the instance.
        producer.start ();
        producer.setRetryTimesWhenSendAsyncFailed (0);

        int messageCount = 100;
        final CountDownLatch countDownLatch = new CountDownLatch (messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message ("Jodie_topic_1023",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes (RemotingHelper.DEFAULT_CHARSET));
                producer.send (msg, new SendCallback () {
                    @Override
                    public void onSuccess (SendResult sendResult) {
                        countDownLatch.countDown ();
                        System.out.printf ("%-10d OK %s %n", index, sendResult.getMsgId ());
                    }

                    @Override
                    public void onException (Throwable e) {
                        countDownLatch.countDown ();
                        System.out.printf ("%-10d Exception %s %n", index, e);
                        e.printStackTrace ();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        countDownLatch.await (5, TimeUnit.SECONDS);
        producer.shutdown ();
    }

    /**
     * Send Messages in One-way Mode 啥也不返回
     * 单向传输用于需要中等可靠性的情况，例如日志收集。
     *
     * @throws MQClientException
     * @throws UnsupportedEncodingException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    public void testSendOneway () throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer ("send-oneway");
        // Specify name server addresses.
        producer.setNamesrvAddr (NAMESRVADDR);
        //Launch the instance.
        producer.start ();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message ("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes (RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway (msg);
        }
        //Wait for sending to complete
        Thread.sleep (5000);
        producer.shutdown ();
    }
}