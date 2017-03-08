package com.hanpfei;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class TestProducer {
    public static void main(String[] args) {
        long events = Long.parseLong(args[0]);
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092 ");
        props.put("bootstrap.servers", "localhost:9092 ");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("page_visits", ip, msg);
            ProducerRecord<String,String> record = new ProducerRecord<String, String>("test", ip, msg);
            producer.send(record);
        }
        producer.close();
    }
}