package rabbitmq.$08_dead_queue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import rabbitmq.uitls.RabbitMqUtils;

/**
 * @ClassName DeadQueueProducer
 * @Description 死信队列 触发的三种情况  1.ttl 2.队列过长 3.被拒绝
 * @Author hike97
 * @Date 2021/10/3 21:32
 * @Version 1.0
 **/
public class DeadQueueProducer {
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    public static void main(String[] argv) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
            /**
             * case01 设置消息的 TTL 时间 模拟死信队列 ttl超时的场景
             */
//            AMQP.BasicProperties properties = new
//                    AMQP.BasicProperties().builder().expiration("10000").build();
            //该信息是用作演示队列个数限制
            for (int i = 1; i <11 ; i++) {
                String message="info"+i;
                channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null,
                        message.getBytes());
                System.out.println("生产者发送消息:"+message);
            }
        }
    }

}
