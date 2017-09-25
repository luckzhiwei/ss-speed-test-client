package com.vecent.ssspeedtest.model;

import android.os.Handler;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private ArrayList<String> mServersForTest;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private RequestCallBack mPingCallBack;

    public static interface RequestCallBack {
        void onAllRequestFinishListener(float timeUsed, int totalReqSize);

        void onOneRequestFinishListener(SpeedTestResult result);
    }

    public SpeedTest(ArrayList<String> serversForTest) {
        this.mServersForTest = serversForTest;
        mThreadPool = new ThreadPool();
        mHandler = new Handler();
    }

    public SpeedTestResult httpSpeedTest(INet net, String serverToPing) {
        return net.getHttpTestResult(serverToPing);
    }

    public void startTest(final INet net) {
        final long startTime = System.currentTimeMillis();
        for (final String servers : mServersForTest) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult pingResult = httpSpeedTest(net, servers);
                    if (pingResult != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPingCallBack.onOneRequestFinishListener(pingResult);
                            }
                        });
                    }
                }
            });
        }
        mThreadPool.stopAddTask();
        try {
            mThreadPool.waitAllTaskComplete();
        } catch (InterruptedException e) {
            LogUtil.logInfo("Wait all task complete error", " InterruptedException");
        }
        final long endTime = System.currentTimeMillis();
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mPingCallBack.onAllRequestFinishListener(1.0f * (endTime - startTime) / 1000, mServersForTest.size());
            }
        });
    }

    public void setPingCallBack(RequestCallBack fun) {
        this.mPingCallBack = fun;
    }

    public void cancel() {
        this.mThreadPool.stopNow();
    }

    public float calConnectRateWhiteList() {
        int whiteListSize, connectServers = 0;

    }

    public float calConnectRateBalckList() {
        return 0.0f;
    }


}
