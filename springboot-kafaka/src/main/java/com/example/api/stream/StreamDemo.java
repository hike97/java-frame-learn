package com.example.api.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

/**
 * @author hike97
 * @create 2021-07-15 16:05
 * @desc 流处理代码
 **/
public class StreamDemo {
    public static void main (String[] args) throws InterruptedException {
        //
        Properties properties = new Properties ();
        properties.put (StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.10:9092");
        properties.put (StreamsConfig.APPLICATION_ID_CONFIG, "phone_count");
        properties.put (StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String ().getClass ());
        properties.put (StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String ().getClass ());
        //设计模式 建造者模式
        StreamsBuilder builder = new StreamsBuilder ();
        //使用流进行统计
        KStream<String, String> countStream = builder.stream ("topicName");
        KTable<String, Long> wordsCount = countStream.flatMapValues (textLine ->
                Arrays.asList (textLine.toLowerCase ().split (" "))).groupBy ((key, word) -> word)
                .count (Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as ("counts"));
        //流处理结果写入到另外一个主题
        wordsCount.toStream ().to ("phone_count", Produced.with (Serdes.String (), Serdes.Long ()));
        KafkaStreams streams = new KafkaStreams (builder.build (), properties);
        //启动Stream引擎
        streams.start ();
        Thread.sleep (5000L);
        builder.build ();
        //将Topic中的流中的数据小写，同时转换成数组，最后变成List


    }
}
