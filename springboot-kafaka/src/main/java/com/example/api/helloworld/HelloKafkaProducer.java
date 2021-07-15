package com.example.api.helloworld;

import com.example.api.constant.ApiConstant;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author hike97
 * @create 2021-07-12 0:45
 * @desc kafka 生产者
 **/
public class HelloKafkaProducer {

    public static void main (String[] args) {
        Properties properties = new Properties ();
        properties.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.10:9092");
        properties.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //        properties.put (ProducerConfig.ACKS_CONFIG, "all");
        //        properties.put (ProducerConfig.RETRIES_CONFIG, "0");
        //        properties.put (ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        //        properties.put (ProducerConfig.LINGER_MS_CONFIG, "1");
        //        properties.put (ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");


        // Producer的主对象
        Producer<String, String> producer = new KafkaProducer<> (properties);
        ProducerRecord<String, String> record;
        try {
            for (int i = 0; i < 4; i++) {
                record = new ProducerRecord<> (ApiConstant.HELLO_TOPIC, null, "hello_kafka");
                //1.发送并忘记（重试会有 有时候会丢失消息）
                //                producer.send (record);
                //2.阻塞同步发送
                Future<RecordMetadata> future = producer.send (record);
                RecordMetadata recordMetadata = future.get ();
                if (null != recordMetadata) {

                }
                //3.异步发送
                producer.send (record, new Callback () {
                    @Override
                    public void onCompletion (RecordMetadata metadata, Exception exception) {
                        if (null != metadata) {
                            System.out.println ("offset:" + metadata.offset () + "-" + "partition" + metadata.partition ());
                        }
                        if (null != exception) {
                            exception.printStackTrace ();
                        }
                    }
                });
                System.out.println (i + ", message is sent");
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            producer.close ();
        }
    }
}
