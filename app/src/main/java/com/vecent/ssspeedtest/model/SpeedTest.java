package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;

import android.os.Handler;
import android.util.Log;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private ArrayList<String> mServersForTest;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private OnPingCallBack mPingCallBack;

    public static interface OnPingCallBack {
        void onPingRetListener(SpeedTestResult result);
    }

    public SpeedTest(ArrayList<String> serversForTest) {
        this.mServersForTest = serversForTest;
        mThreadPool = ThreadPool.getInstance();
        mHandler = new Handler();
    }

    public SpeedTestResult httpSpeedTest(INet net, String serverToPing) {
        return net.getHttpTestResult(serverToPing);
    }


    public void startTest(final INet net) {
        long startTime = System.currentTimeMillis();
        for (final String servers : mServersForTest) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult pingResult = httpSpeedTest(net, servers);
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

}
