package com.bfxy.rocketmq.quickstart;

import java.util.List;

import org.apache.rocketmq.client.consumer.AllocateMessageQueueStrategy;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.bfxy.rocketmq.constants.Const;

public class Consumer {


    public static void main (String[] args) throws MQClientException {
        //创建消费组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer ("test_quick_consumer_name");
        //
        //		consumer.setNamesrvAddr(Const.NAMESRV_ADDR_SINGLE);
        consumer.setNamesrvAddr (Const.NAMESRV_ADDR_MASTER_SLAVE);
        //从消费最后开始消费
        consumer.setConsumeFromWhere (ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //接受topic所有的消息
        consumer.subscribe ("test_quick_topic", "*");
        //创建监听
        consumer.registerMessageListener (new MessageListenerConcurrently () {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage (List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt me = msgs.get (0);
                try {
                    String topic = me.getTopic ();
                    String tags = me.getTags ();
                    String keys = me.getKeys ();
                    //模拟异常
                    //					if(keys.equals("key1")) {
                    //						System.err.println("消息消费失败..");
                    //						int a = 1/0;
                    //					}

                    String msgBody = new String (me.getBody (), RemotingHelper.DEFAULT_CHARSET);
                    System.err.println ("topic: " + topic + ",tags: " + tags + ", keys: " + keys + ",body: " + msgBody);
                } catch (Exception e) {
                    e.printStackTrace ();
                    //记录消息重发多少次
                    //					int recousumeTimes = me.getReconsumeTimes();
                    //					System.err.println("recousumeTimes: " + recousumeTimes);
                    //					if(recousumeTimes == 3) {
                    //						//		记录日志....
                    //						//  	做补偿处理
                    //						//  发了三次 默认成功
                    //						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    //					}
                    //回执失败
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //回执成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start ();
        System.err.println ("consumer start...");

    }
}
