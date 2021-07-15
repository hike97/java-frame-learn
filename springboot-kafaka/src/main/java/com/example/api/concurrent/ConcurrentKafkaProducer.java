package com.example.api.concurrent;

import com.example.api.constant.ApiConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.kafka.clients.producer.*;


import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hike97
 * @create 2021-07-12 22:32
 * @desc 高并发生产者 线程安全
 **/
public class ConcurrentKafkaProducer {

    private static final int MSG_SIZE = 1000;

    private static ExecutorService executorService =
            Executors.newFixedThreadPool (Runtime.getRuntime ().availableProcessors ());

    private static CountDownLatch countDownLatch = new CountDownLatch (MSG_SIZE);

    /**
     * 发送消息的任务
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ProducerWorker implements Runnable {
        private ProducerRecord<String, String> record;
        private KafkaProducer<String, String> producer;

        @Override
        public void run () {
            String id = Thread.currentThread ().getId () + "-" + System.identityHashCode (producer);
            try {
                producer.send (record, new Callback () {
                    @Override
                    public void onCompletion (RecordMetadata metadata, Exception exception) {
                        if (null != metadata) {
                            System.out.println ("offset:" + metadata.offset () + "-" + "partition: " + metadata.partition ());
                        }
                        if (null != exception) {
                            exception.printStackTrace ();
                        }
                    }
                });
                System.out.println (id + "数据【" + record + "】已发送。");
            } catch (Exception e) {
                e.printStackTrace ();
            } finally {
                countDownLatch.countDown ();
            }
        }
    }

    private static DemoUser makeUser (int id) {
        DemoUser user = new DemoUser (id, "xiangxue_" + id);
        return user;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DemoUser {
        int id;
        String userName;
    }

    public static void main (String[] args) {

        Properties properties = new Properties ();
        /**
         * 必须填的
         * */
        //#地址 序列化 反序列化 不能为空
        properties.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.10:9092");
        properties.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        /**
         * 重要的
         * */
        //ack机制  0发了就不管了 1只发送给首领分区  all发给所有
        properties.put (ProducerConfig.ACKS_CONFIG, "all");
        //batch.size 一个批次可以使用的内存大小 16k
        properties.put (ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        //指定生产者在发送批次前等待更多消息加入批次时间
        properties.put (ProducerConfig.LINGER_MS_CONFIG, 0L);
        //发送请求的最大大小 与kafka.message.max.bytes 有关
        properties.put (ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "1*1024*1024");

        /**
         * 次要的
         * */
        //重发消息次数 默认无限重试
        properties.put (ProducerConfig.RETRIES_CONFIG, "0");
        //生产者内存缓冲区大小 默认32M
        properties.put (ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        //客户端最大请求等待时间
        properties.put (ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30 * 1000);
        //最大阻塞时间
        properties.put (ProducerConfig.MAX_BLOCK_MS_CONFIG, 60 * 1000);
        //预压缩数据的压缩类型
        properties.put (ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        /**
         * 保证重试失败的消息的顺序
         */
        //类似独占锁 重试的过程中 其它客户端无法对当前分区发送消息 严重影响吞吐量
        properties.put (ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);


        // Producer的主对象
        KafkaProducer<String, String> producer = new KafkaProducer<> (properties);

        try {
            for (int i = 0; i < MSG_SIZE; i++) {
                DemoUser demoUser = makeUser (i);
                ProducerRecord<String, String> record = new ProducerRecord<> (
                        ApiConstant.CONCURRENT_USER_INFO_TOPIC, null,
                        System.currentTimeMillis (),
                        demoUser.getId () + "", demoUser.toString ());
                executorService.submit (new ProducerWorker (record, producer));
            }
            countDownLatch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

    }
}
