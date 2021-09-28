package rabbitmq.history.topic;

import com.rabbitmq.client.*;
import rabbitmq.history.common.ConnectionUtil;

import java.io.IOException;

/**
 * @ClassName TopicConsumer02
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/6 14:39
 * @Version 1.0
 **/
public class TopicConsumer02 {
    private final static String QUEUE_NAME = "topic_exchange_queue_Q2";
    private final static String EXCHANGE_NAME = "test_topic_exchange";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机，同时指定需要订阅的routing key。订阅关于兔子以及懒惰动物的消息
        /**
         * #：匹配一个或多个词
         * *：匹配不多不少恰好1个词
         */
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "lazy.#");

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [消费者2] received : " + msg + "!");
            }
        };
        // 监听队列，自动ACK
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
