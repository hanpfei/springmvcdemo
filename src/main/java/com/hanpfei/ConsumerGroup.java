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

public class ConsumerGroup {
    private final ConsumerConnector consumer;
    private final String topic;
    private  ExecutorService executor;

    public ConsumerGroup(Properties props, String topic) {
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        this.topic = topic;
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

    public void start(int numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        executor = Executors.newFixedThreadPool(numThreads);

        // now create an object to consume the messages
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new ConsumerTest(stream, threadNumber));
            threadNumber++;
        }
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (false) {
                break;
            }
        }
        shutdown();
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