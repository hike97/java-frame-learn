package rabbitmq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.common.ConnectionUtil;

/**
 * @ClassName Send
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/2 16:36
 * @Version 1.0
 **/
public class Send02 {
    //交换机类型为fanout
    /**
     * 1） 声明Exchange，不再声明Queue
     * 2） 发送消息到Exchange，不再发送到Queue
     */
//    private final static String EXCHANGE_NAME = "test_fanout_exchange";
    private final static String EXCHANGE_NAME = "test_direct_exchange";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为fanout
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 声明exchange，指定类型为 direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "注册成功！！";
        // 发布消息到Exchange
//        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        //direct 交换机类型 只能直接发送给sms queue类型
//        channel.basicPublish(EXCHANGE_NAME, "sms", null, message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "email", null, message.getBytes());

        System.out.println(" [生产者] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
