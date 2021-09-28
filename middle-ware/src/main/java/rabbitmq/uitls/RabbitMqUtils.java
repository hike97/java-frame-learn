package rabbitmq.uitls;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author hike97
 * @create 2021-09-27 12:21
 * @desc 工具类
 **/
public class RabbitMqUtils {

    //得到一个连接的 channel
    public static Channel getChannel () throws Exception {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory ();
        factory.setHost ("192.168.3.10");
        factory.setUsername ("admin");
        factory.setPassword ("123");
        Connection connection = factory.newConnection ();
        Channel channel = connection.createChannel ();
        return channel;
    }
}
