package com.hanpfei;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/")
    public @ResponseBody String index() {
        return "index";
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        List<String> names = new ArrayList<>();
        return new Greeting(counter.incrementAndGet(), String.format(template, name) + "中文");
    }

    private void sendMessageToKafka0_8(String sentMsg) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<String, String>("test", sentMsg, sentMsg));

        producer.close();
    }

    private void sendMessageToKafka0_10(String sentMsg) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9095");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<String, String>("test", sentMsg, sentMsg));

        producer.close();
    }

    @RequestMapping("/sendMsg")
    public Greeting message(@RequestParam(value="msg", defaultValue="World") String msg) {
        String sentMsg = String.format(template, msg);
        sendMessageToKafka0_8(sentMsg);
        sendMessageToKafka0_10(sentMsg);
        return new Greeting(counter.incrementAndGet(), sentMsg);
    }

    private static class Kafka0_10MessageConsumeTask extends Thread {
        @Override
        public void run() {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9095");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//            consumer.subscribe(Arrays.asList("test", "bar"));
            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(10000);
//                for (ConsumerRecord<String, String> record : records)
//                    System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
        }
    }

    private static class Kafka0_8MessageConsumeTask extends Thread {
        @Override
        public void run() {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//            consumer.subscribe(Arrays.asList("test", "bar"));
            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(10000);
//                for (ConsumerRecord<String, String> record : records)
//                    System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
        }
    }

    @RequestMapping("/recvMsg")
    public Greeting recvMessage() {
        new Kafka0_8MessageConsumeTask().start();
        new Kafka0_10MessageConsumeTask().start();
        return new Greeting(counter.incrementAndGet(), template);
    }
}
