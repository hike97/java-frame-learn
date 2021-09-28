package rabbitmq.$01_hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static rabbitmq.$01_hello.Producer.QUEUE_NAME;

/**
 * @author hike97
 * @create 2021-09-27 12:06
 * @desc hello 消费者
 **/
public class Consumer {
    public static void main (String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory ();
        factory.setHost ("192.168.3.10");
        factory.setUsername ("admin");
        factory.setPassword ("123");
        Connection connection = factory.newConnection ();
        Channel channel = connection.createChannel ();
        System.out.println ("等待接收消息....");

        DeliverCallback deliverCallback = new DeliverCallback () {
            @Override
            public void handle (String consumerTag, Delivery message) throws IOException {
                //消息包括 消息头 消息参数 消息体
                System.out.println (new String (message.getBody ()));
            }
        };

        CancelCallback cancelCallback = new CancelCallback () {
            @Override
            public void handle (String consumerTag) throws IOException {
                System.out.println ("消息消费被中断");
            }
        };

        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         * 3.消费者未成功消费的回调
         */
        //String basicConsume(String queue, DeliverCallback deliverCallback, CancelCallback cancelCallback) throws IOException;
        //这里需要两个函数式接口 一个接收消息一个取消消息
        channel.basicConsume (QUEUE_NAME, true, deliverCallback, cancelCallback);


    }
}
