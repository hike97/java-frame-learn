package rabbitmq.$10_mianshi;


import java.io.IOException;

/**
 * @ClassName ReaptableConsume
 * @Description 重复消费伪代码
 * @Author hike97
 * @Date 2021/10/5 13:45
 * @Version 1.0
 **/
public class ReaptableConsume {

    public static void main (String[] args) {
        /**重复消费问题**/
        //        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate ();
        //        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent("orderNo+couponId");
        //        //先检查这条消息是不是已经消费过了
        //        if (!Boolean.TRUE.equals(flag)) {
        //            return;
        //        }
        //        //执行业务...
        //        //消费过的标识存储到 Redis，10 秒过期
        //        stringRedisTemplate.opsForValue().set("orderNo+couponId","1",Duration.ofSeconds(10L));

        /**顺序消费问题**/

        //        spring:
        //        rabbitmq:
        //        listener:
        //        simple:
        //        prefetch: 1 #每次只推送一个消息
        //        acknowledge-mode: manual
        /**消费积压问题**/
        //        对生产者发消息接口进行适当限流（不太推荐，影响用户体验）
        //        多部署几台消费者实例（推荐）
        //        适当增加 prefetch 的数量，让消费端一次多接受一些消息（推荐，可以和第二种方案一起用）
        /**消息丢失问题解决 从生产者 mq (exchange queue) 消费者三个方面谈
         * 生产者丢了 有回调函数 发送交换机回调 queue回退 回调
         * rabbitMQ丢失 需要持久化 交换机持久化 和 queue持久化
         * 消费者丢失 调用消费者回调 不推荐
         *          使用springboot 重试机制
         * **/
        //        生产者消息没到交换机，相当于生产者弄丢消息
        //        交换机没有把消息路由到队列，相当于生产者弄丢消息
        //        RabbitMQ 宕机导致队列、队列中的消息丢失，相当于 RabbitMQ 弄丢消息
        //        消费者消费出现异常，业务没执行，相当于消费者弄丢消息
        /**
         * 开启手动ack
         * spring:
         *   rabbitmq:
         *     listener:
         *       simple:
         *         acknowledge-mode: manual #手动应答
         */
        //拒收代码逻辑
        //        @RabbitListener(queues = "queue")
        //        public void listen(String object, Message message, Channel channel) {
        //            long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //            log.info("消费成功：{},消息内容:{}", deliveryTag, object);
        //            try {
        //                /**
        //                 * 执行业务代码...
        //                 * */
        //                channel.basicAck(deliveryTag, false);
        //            } catch (IOException e) {
        //                log.error("签收失败", e);
        //                try {
        //                    channel.basicNack(deliveryTag, false, true);
        //                } catch (IOException exception) {
        //                    log.error("拒签失败", exception);
        //                }
        //            }
        //        }
        //springboot 控制
        //        spring:
        //        rabbitmq:
        //        listener:
        //        simple:
        //        retry:
        //        enabled: true
        //        max-attempts: 3 #重试次数


    }

}
