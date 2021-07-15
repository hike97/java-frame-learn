package com.site.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author hike97
 * @create 2021-07-09 19:53
 * @desc
 **/
public class RocketConsumer {

    static final String NAMESRVADDR = "192.168.3.10:9876";

    /**
     * pushConsumer 等待消息推送不做任何动作
     */
    @Test
    public void testDefaultPushConsumer () throws MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer ("default-push-a");

        // Specify name server addresses.
        consumer.setNamesrvAddr (NAMESRVADDR);
        // 设置消费模式 默认是Cluster 可调整为广播模式
        //        consumer.setMessageModel (MessageModel.BROADCASTING);
        consumer.setMessageModel (MessageModel.CLUSTERING);

        // Subscribe one more more topics to consume.
        consumer.subscribe ("TopicTest1234", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener (new MessageListenerConcurrently () {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage (List<MessageExt> msgs,
                                                             ConsumeConcurrentlyContext context) {
                System.out.printf ("%s Receive New Messages: %s %n", Thread.currentThread ().getName (), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start ();
        System.out.printf ("Consumer Started.%n");
        while (true) {

        }
    }

    /**
     * 用 pull的方式 拉取消息 方法已过期
     */
    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long> ();

    @Test
    public void testPullConsumer () throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer ("default-pull");
        consumer.setNamesrvAddr (NAMESRVADDR);
        consumer.start ();
        //获取到整个主题下的所有queue
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues ("Jodie_topic_1023");
        for (MessageQueue mq : mqs) {
            System.out.printf ("Consume from the queue: %s%n", mq);
            SINGLE_MQ:
            while (true) {
                try {
                    //先get mq的最新消费index offset
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound (mq, null, getMessageQueueOffset (mq), 32);
                    System.out.printf ("%s%n", pullResult);
                    //获取完之后 更新新的offset
                    putMessageQueueOffset (mq, pullResult.getNextBeginOffset ());
                    switch (pullResult.getPullStatus ()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }

        consumer.shutdown ();
        /**
         * 输出结果
         * Consume from the queue: MessageQueue [topic=TopicTest, brokerName=broker-a, queueId=3]
         * PullResult [pullStatus=FOUND, nextBeginOffset=32, minOffset=0, maxOffset=50, msgFoundList=32]
         * PullResult [pullStatus=FOUND, nextBeginOffset=50, minOffset=0, maxOffset=50, msgFoundList=18]
         */

    }

    private static long getMessageQueueOffset (MessageQueue mq) {
        Long offset = OFFSE_TABLE.get (mq);
        if (offset != null) {
            return offset;
        }

        return 0;
    }

    private static void putMessageQueueOffset (MessageQueue mq, long offset) {
        OFFSE_TABLE.put (mq, offset);
    }

    /**
     * 新的pull方式 将messageQueue 的 状态维护去掉 4.3 没有
     */

    public static volatile boolean running = true;

    @Test
    public void testDefaultLitePullConsumer () throws Exception {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer ("lite_pull_consumer_test");
        litePullConsumer.setNamesrvAddr (NAMESRVADDR);
        litePullConsumer.setConsumeFromWhere (ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        litePullConsumer.subscribe ("Jodie_topic_1023", "*");
        litePullConsumer.start ();
        try {
            while (running) {
                List<MessageExt> messageExts = litePullConsumer.poll ();
                System.out.printf ("%s%n", messageExts);
            }
        } finally {
            litePullConsumer.shutdown ();
        }
    }

    /**
     * 指定数据拉取
     */
    @Test
    public void testLitePullConsumerAssign () throws Exception {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer ("lite_pull_consumer_test_assign");
        litePullConsumer.setNamesrvAddr (NAMESRVADDR);
        litePullConsumer.setAutoCommit (false);
        litePullConsumer.start ();
        Collection<MessageQueue> mqSet = litePullConsumer.fetchMessageQueues ("Jodie_topic_1023");
        List<MessageQueue> list = new ArrayList<> (mqSet);
        List<MessageQueue> assignList = new ArrayList<> ();
        for (int i = 0; i < list.size () / 2; i++) {
            assignList.add (list.get (i));
        }
        //指定某个队列进行数据拉取
        litePullConsumer.assign (assignList);
        litePullConsumer.seek (assignList.get (0), 10);
        try {
            while (running) {
                List<MessageExt> messageExts = litePullConsumer.poll ();
                System.out.printf ("%s %n", messageExts);
                litePullConsumer.commitSync ();
            }
        } finally {
            litePullConsumer.shutdown ();
        }
    }
}
