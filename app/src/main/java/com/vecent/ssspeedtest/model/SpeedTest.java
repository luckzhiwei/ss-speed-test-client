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

    private List<Server> mServersForTest;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private OnPingCallBack mPingCallBack;

    public static interface OnPingCallBack {
        void onPingRetListener(SpeedTestResult result);
    }

    public SpeedTest(List<Server> serversForTest) {
        this.mServersForTest = serversForTest;
        mThreadPool = new ThreadPool();
        mHandler = new Handler();
    }

    public SpeedTestResult httpSpeedTest(INet net, String serverToPing) {
        return net.getHttpTestResult(serverToPing);
    }

    public void startTest(final INet net) {
        long startTime = System.currentTimeMillis();
        for (final Server server2Test : mServersForTest) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult pingResult = httpSpeedTest(net, server2Test.getWeb());
                    if (pingResult != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPingCallBack.onPingRetListener(pingResult);
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
        long endTime = System.currentTimeMillis();
        LogUtil.logInfo(getClass().getName(), "All task complete");
        LogUtil.logDebug(getClass().getName(), (endTime - startTime) + " totalTImeUsed");
    }

    public void setPingCallBack(OnPingCallBack fun) {
        this.mPingCallBack = fun;
    }

    public void cancel() {
        this.mThreadPool.stopNow();
    }


}
