package com.hanpfei;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.log4j.Logger;

import java.util.List;

public class NapmKafkaConsumer {
    private static Logger logger = Logger.getLogger(NapmKafkaConsumer.class);

    private final KafkaStream mStream;
    private final int mThreadNumber;

    private final List<String> mDataSource;
    private final Object mDataSourceLock;

    public NapmKafkaConsumer(KafkaStream stream, int threadNumber, List<String> dataSource,
                             Object dataSourceLock) {
        mThreadNumber = threadNumber;
        mStream = stream;
        mDataSource = dataSource;
        mDataSourceLock = dataSourceLock;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = mStream.iterator();
        while (GlobalConst.RUNNING && it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> msg = it.next();
            String msgStr = new String(msg.message());
            synchronized (mDataSourceLock) {
                if (mDataSource.size() < 100) {
                    mDataSource.add(msgStr);
                }
            }
            logger.info("Thread " + mThreadNumber + ": " + msgStr);
        }
        logger.info("Shutting down Thread: " + mThreadNumber);
    }
}
