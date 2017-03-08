package com.hanpfei;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hanpfei0306 on 17-3-3.
 */
public class MsgConvertAndUploadTask implements Runnable {
    private static Logger logger = Logger.getLogger(MsgConvertAndUploadTask.class);

    private final ScheduledExecutorService mMsgConverterExecutor;
    private final List<String> mDataSource;
    private final Object mDataLock;

    public MsgConvertAndUploadTask(ScheduledExecutorService executorService, List<String> dataSource,
                                    Object dataLock) {
        mMsgConverterExecutor = executorService;
        mDataSource = dataSource;
        mDataLock = dataLock;
    }

    private void uploadMsgs(List<String> dataItems) {
        for (String dataItem : dataItems) {
            logger.info(dataItem);
        }
    }

    @Override
    public void run() {
        List<String> dataItems = new ArrayList<String>();
        synchronized (mDataLock) {
            if (!mDataSource.isEmpty()) {
                int startIndex = 0;
                int endIndex = mDataSource.size() < 5 ? mDataSource.size() : 5;
                dataItems.addAll(mDataSource.subList(startIndex, endIndex));
                mDataSource.removeAll(dataItems);
            }
        }
        uploadMsgs(dataItems);

        mMsgConverterExecutor.schedule(this, 100, TimeUnit.MICROSECONDS);
    }
}
