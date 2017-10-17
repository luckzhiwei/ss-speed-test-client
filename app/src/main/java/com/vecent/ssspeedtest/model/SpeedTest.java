package com.vecent.ssspeedtest.model;

import android.os.Handler;

import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.List;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private List<Server> mServers2Test;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private RequestCallBack mPingCallBack;
    private final int tottalSize;
    private long startTime;

    public interface RequestCallBack {
        void onAllRequestFinishListener(float timeUsed, int totalReqSize);

        void onOneRequestFinishListener(SpeedTestResult result, int totalSize);
    }

    public SpeedTest(List<Server> serversForTest) {
        this.mServers2Test = serversForTest;
        this.tottalSize = serversForTest.size();
        mThreadPool = new ThreadPool();
        mHandler = new Handler();
    }

    public SpeedTestResult httpSpeedTest(INet net, Server server2Request) {
        return net.getHttpTestResult(server2Request);
    }

    public void startTest(final INet net) {
        this.startTime = System.currentTimeMillis();
        for (final Server server : mServers2Test) {

            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult pingResult = httpSpeedTest(net, server);
                    if (pingResult != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPingCallBack.onOneRequestFinishListener(pingResult, tottalSize);
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
                mPingCallBack.onAllRequestFinishListener(1.0f * (endTime - startTime) / 1000, mServers2Test.size());
            }
        });
    }

    public void setPingCallBack(RequestCallBack fun) {
        this.mPingCallBack = fun;
    }

    public void cancel() {
        this.mThreadPool.stopNow();
    }

    public float calConnectRateWhiteList(List<SpeedTestResult> result) {
        int whiteListSize = 0, connectServers = 0;
        for (SpeedTestResult oneRet : result) {
            if (oneRet.isWhiteAddr()) {
                whiteListSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectServers++;
                }
            }
        }
        if (whiteListSize == 0) {
            return 0;
        }
        return (1.0f * connectServers / whiteListSize);
    }

    public float calConnectRateBalckList(List<SpeedTestResult> result) {
        int blackListSize = 0, connectServers = 0;
        for (SpeedTestResult oneRet : result) {
            if (!oneRet.isWhiteAddr()) {
                blackListSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectServers++;
                }
            }
        }
        if (blackListSize == 0) {
            return 0;
        }
        return (1.0f * connectServers / blackListSize);
    }

    public int countWhiteListAddr(List<SpeedTestResult> result) {
        int count = 0;
        for (SpeedTestResult server : result) {
            if (server.isWhiteAddr()) {
                count++;
            }
        }
        return count;
    }

    public int countBlackListAddr(List<SpeedTestResult> result) {
        int count = 0;
        for (SpeedTestResult server : result) {
            if (!server.isWhiteAddr()) {
                count++;
            }
        }
        return count;
    }

    public long getTimeStart() {
        return startTime;
    }


}
