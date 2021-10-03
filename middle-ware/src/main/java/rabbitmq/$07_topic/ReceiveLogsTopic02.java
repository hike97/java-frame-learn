package rabbitmq.$07_topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.uitls.RabbitMqUtils;

/**
 * @author hike97
 * @create 2021-09-27 21:32
 * @desc topic交换机
 **/
public class ReceiveLogsTopic02 {
    //交换机的名称
    private static final String EXCHANGE_NAME = "topic_logs";
    public static final String QUEUE_NAME = "Q2";

    public static void main (String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel ();
        channel.exchangeDeclare (EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //声明一个队列
        channel.queueDeclare (QUEUE_NAME,false,false,false,null);
        //绑定交换机与队列
        // * 匹配一个单词
        // # 匹配多个单词
        channel.queueBind (QUEUE_NAME,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind (QUEUE_NAME,EXCHANGE_NAME,"lazy.#");
        //接收消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println ("控制台ReceiveLogsTopic01打印接收到的消息：" + new String (message.getBody (), "UTF-8"));
            System.out.println ("交换机："+message.getEnvelope ().getExchange ()+"路由键：" + message.getEnvelope ().getRoutingKey ());
        };
        channel.basicConsume (QUEUE_NAME, true,deliverCallback,consumerTag -> {});
    }
}
