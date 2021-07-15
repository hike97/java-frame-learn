package com.site.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hike97
 * @create 2021-07-09 22:39
 * @desc
 **/
public class OrderProducerAndConsumer {

    static final String NAMESRVADDR = "192.168.3.10:9876";

    /**
     * 局部有序 全局无序
     */
    @Test
    public void testOrderProducer () {
        try {
            DefaultMQProducer producer = new DefaultMQProducer ("order-producer");
            producer.setNamesrvAddr (NAMESRVADDR);
            producer.start ();

            //            String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
            //10个订单
            for (int i = 0; i < 10; i++) {
                int orderId = i;
                //6个步骤
                for (int j = 0; j <= 5; j++) {
                    Message msg =
                            new Message ("order-test", "order_" + orderId, "KEY" + orderId,
                                    ("order_" + orderId + " step" + j).getBytes (RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send (msg, new MessageQueueSelector () {
                        /**
                         *
                         * @param mqs topic 下的所有mq
                         * @param msg 要发的消息
                         * @param arg 表示orderId
                         * @return
                         */
                        @Override
                        public MessageQueue select (List<MessageQueue> mqs, Message msg, Object arg) {
                            Integer id = (Integer) arg;
                            //一个固定的Id放到一个messageQueue里
                            int index = id % mqs.size ();
                            return mqs.get (index);
                        }
                    }, orderId);

                    System.out.printf ("%s%n", sendResult);
                }
            }

            //producer.shutdown();
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace ();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ();
        }
    }

    /**
     * 顺序消费 用的new MessageListenerOrderly
     * 普通消费 用的new MessageListenerConcurrently
     *
     * @throws MQClientException
     */
    @Test
    public void testOrderConsumer () throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer ("order-consume-no");
        consumer.setNamesrvAddr (NAMESRVADDR);
        consumer.setConsumeFromWhere (ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe ("order-test", "*");
        //一个一个队列拿消息
        consumer.registerMessageListener (new MessageListenerOrderly () {
            AtomicLong consumeTimes = new AtomicLong (0);

            @Override
            public ConsumeOrderlyStatus consumeMessage (List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit (true);
                for (MessageExt msg : msgs) {
                    System.out.println ("收到消息内容： " + new String (msg.getBody ()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        //换成Concurrently 无序
        //        consumer.registerMessageListener(new MessageListenerConcurrently () {
        //            AtomicLong consumeTimes = new AtomicLong(0);
        //
        //            @Override
        //            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        //                for (MessageExt msg : msgs) {
        //                    System.out.println ("收到消息内容： " + new String (msg.getBody ()));
        //                }
        //                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        //            }
        //        });

        consumer.start ();
        System.out.printf ("Consumer Started.%n");
        for (; ; ) ;

    }
}
