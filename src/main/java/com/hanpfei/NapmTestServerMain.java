package com.hanpfei;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @Description
 * @author hzdaiyuan
 * @date 2016-11-14 下午7:08:24
 */
public class NapmTestServerMain {
    /**
     * File name for config.
     */
    private static final String CONFIG_FILE = "napm-consumer.properties";

    private static Logger logger = Logger.getLogger(NapmTestServerMain.class);

    private ThreadPoolExecutor mExecutor = null;
    private ConsumerConnector mTestServerConsumer;
    private ScheduledExecutorService mMsgConverterExecutor;

    private List<String> mDataSource = new ArrayList<String>();
    private Object mDataSourceLock = new Object();

    private void startExecutor(int consumerSize) {
        mExecutor = new ThreadPoolExecutor(consumerSize, consumerSize, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(consumerSize), new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger(0);
            private ThreadGroup threadGroup = new ThreadGroup("apm-kafka-test-server");

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(threadGroup, r, "consumer-pool-" + count.getAndIncrement());
                return thread;
            }
        });

        mMsgConverterExecutor = new ScheduledThreadPoolExecutor(1);
        MsgConvertAndUploadTask uploadTask = new MsgConvertAndUploadTask(mMsgConverterExecutor,
                mDataSource, mDataSourceLock);
        mMsgConverterExecutor.schedule(uploadTask, 100, TimeUnit.MICROSECONDS);
    }

    public void startConsumer() {
        Properties props = new Properties();
//        Properties consumerProp = ConfigUtils.loadKafkaConsumerConfig(CONFIG_FILE, props);

        int consumerSize = Integer.valueOf(props.getProperty("consumer.processor.size", "2"));
        logger.info("startConsumer: size=" + consumerSize);
        startExecutor(consumerSize);

        String mobileTopic = props.getProperty("mobile.topic");
        String groupId = props.getProperty("group.id");
        String zookeeperConnect = props.getProperty("zookeeper.connect");

        logger.info("mobileTopic =" + mobileTopic);
        logger.info("groupId =" + groupId);
        logger.info("zookeeperConnect =" + zookeeperConnect);

//        consumerProp.put("zookeeper.connect", zookeeperConnect);
//        consumerProp.put("group.id", groupId);

        kafka.consumer.ConsumerConfig consumerConfig = new kafka.consumer.ConsumerConfig(props);
        mTestServerConsumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        startConsumer(mobileTopic, consumerSize);
    }

    public void startConsumer(String topic, int numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = mTestServerConsumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now create an object to consume the messages
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            mExecutor.submit(new ConsumerTask(stream, threadNumber));
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
    }

    public void shutdown() {
        GlobalConst.RUNNING = false;
        if (mTestServerConsumer != null) mTestServerConsumer.shutdown();
        if (mExecutor != null) mExecutor.shutdown();
        try {
            if (!mExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                logger.info("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            logger.info("Interrupted during shutdown, exiting uncleanly");
        }
    }

    class ConsumerTask implements Runnable {
        NapmKafkaConsumer mKafkaConsumer;

        public ConsumerTask(KafkaStream stream, int threadNumber) {
            mKafkaConsumer = new NapmKafkaConsumer(stream, threadNumber, mDataSource, mDataSourceLock);
        }

        public void run() {
            mKafkaConsumer.run();
        }
    }

    public static void main(String[] args) throws Exception {
        class ShutdownTask implements Runnable {
            private NapmTestServerMain mApplication;

            public ShutdownTask(NapmTestServerMain agent) {
                this.mApplication = agent;
            }

            @Override
            public void run() {
                mApplication.shutdown();
                logger.info("Test server consumer halted");
            }
        }

        NapmTestServerMain consumer = new NapmTestServerMain();
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownTask(consumer)));
            consumer.startConsumer();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
