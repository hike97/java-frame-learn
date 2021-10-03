package rabbitmq.$06_direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import rabbitmq.uitls.RabbitMqUtils;

import java.io.IOException;

/**
 * @author hike97
 * @create 2021-09-27 21:32
 * @desc 直接交还击
 **/
public class ReceiveLogsDirect01 {
    //交换机的名称
    private static final String EXCHANGE_NAME = "direct_logs";
    public static final String QUEUE_NAME = "console";

    public static void main (String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel ();
        channel.exchangeDeclare (EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列
        channel.queueDeclare (QUEUE_NAME,false,false,false,null);
        //绑定交换机与队列
        channel.queueBind (QUEUE_NAME,EXCHANGE_NAME,"info");
        channel.queueBind (QUEUE_NAME,EXCHANGE_NAME,"warning");
        //接收消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println ("控制台ReceiveLogsDirect01打印接收到的消息：" + new String (message.getBody (), "UTF-8"));
        channel.basicConsume (QUEUE_NAME, true,deliverCallback,consumerTag -> {});
    }
}
