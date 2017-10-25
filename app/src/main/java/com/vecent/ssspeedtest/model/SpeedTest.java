package com.vecent.ssspeedtest.model;

import android.os.Handler;

import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
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

    public void countResult(TotalSpeedTestResult total, List<SpeedTestResult> result) {
        int whiteAddrSize = 0, connectWhiteAddrSize = 0,
                blackAddrSize = 0, connectBlackAddrSize = 0,
                whiteAddrSpeedAvgSum = 0, blackAddrSpeedAvgSum = 0;
        for (SpeedTestResult oneRet : result) {
            if (oneRet.isWhiteAddr()) {
                whiteAddrSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectWhiteAddrSize++;
                    whiteAddrSpeedAvgSum += oneRet.getDownLoadSpeed();

                }
            } else {
                blackAddrSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectBlackAddrSize++;
                    blackAddrSpeedAvgSum += oneRet.getDownLoadSpeed();
                }
            }
        }
        if (whiteAddrSize == 0) {
            total.setWhiteAddrServerCount(0);
            total.setWhiteAddrConnectSuccesRate(0);
            total.setSpeedWhiteAddrDownLoadAvg(0.0f);
        } else {
            total.setWhiteAddrServerCount(whiteAddrSize);
            total.setWhiteAddrConnectSuccesRate(1.0f * connectWhiteAddrSize / whiteAddrSize);
            if (connectWhiteAddrSize == 0) {
                total.setSpeedWhiteAddrDownLoadAvg(0.0f);
            } else {
                total.setSpeedWhiteAddrDownLoadAvg(whiteAddrSpeedAvgSum * 1.0f / connectWhiteAddrSize);
            }
        }
        if (blackAddrSize == 0) {
            total.setBlackAddrServerCount(0);
            total.setBlackAddrConnectSuccesRate(0);
            total.setSpeedBlackAddrDownLoadAvg(0.0f);
        } else {
            total.setBlackAddrServerCount(blackAddrSize);
            total.setBlackAddrConnectSuccesRate(1.0f * connectBlackAddrSize / blackAddrSize);
            if (connectBlackAddrSize == 0) {
                total.setSpeedBlackAddrDownLoadAvg(0);
            } else {
                total.setSpeedBlackAddrDownLoadAvg(1.0f * blackAddrSpeedAvgSum / connectBlackAddrSize);
            }
        }
        total.setCurServerCount(whiteAddrSize + blackAddrSize);
    }


    public long getTimeStart() {
        return startTime;
    }


}
