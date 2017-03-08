package com.hanpfei;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConsumerGroupExample {
    private final ConsumerConnector consumer;
    private final String topic;
    private  ExecutorService executor;

    public ConsumerGroupExample(Properties props, String a_topic) {
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        this.topic = a_topic;
    }

    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }

    public void run(int a_numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        //
        executor = Executors.newFixedThreadPool(a_numThreads);

        // now create an object to consume the messages
        //
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new ConsumerTest(stream, threadNumber));
            threadNumber++;
        }
    }

    private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();

        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new ConsumerConfig(props);
    }

    public static void main(String[] args) {
        String zooKeeper = "localhost:2181";
        String groupId = "rawdata";
        String topic = "test";
        int threads = 2;
        ConsumerConfig consumerConfig = createConsumerConfig(zooKeeper, groupId);

        Properties props = new Properties();

        props.put("zookeeper.connect", zooKeeper);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        ConsumerGroupExample example = new ConsumerGroupExample(props, topic);
        example.run(threads);

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ie) {
            }
            if (false) {
                break;
            }
        }
        example.shutdown();
    }

    public class ConsumerTest implements Runnable {
        private KafkaStream m_stream;
        private int m_threadNumber;

        public ConsumerTest(KafkaStream a_stream, int a_threadNumber) {
            m_threadNumber = a_threadNumber;
            m_stream = a_stream;
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
            while (it.hasNext())
                System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
            System.out.println("Shutting down Thread: " + m_threadNumber);
        }
    }
}