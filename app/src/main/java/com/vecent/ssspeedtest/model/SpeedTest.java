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

    public SpeedTestResult ping(INet net, String serverToPing) {
        SpeedTestResult pingRet = net.httpRequest(serverToPing);
        return pingRet;
    }


    public void startTest(final INet net) {

        for (final String servers : mServersForTest) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult pingResult = ping(net, servers);
                    if (pingResult != null) {
                        if (pingResult.getExceptionMsg() != null) {
                            LogUtil.logDebug(getClass().getName(),pingResult.getExceptionMsg()+"excepiton");
                        }
                        LogUtil.logDebug(getClass().getName(), pingResult.getRequestServer());
                        LogUtil.logDebug(getClass().getName(), pingResult.getTotalSize() + "size");
                        LogUtil.logDebug(getClass().getName(), pingResult.getStatusCode() + "code");
                        LogUtil.logDebug(getClass().getName(), pingResult.getTimeUsed() + "time");
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPingCallBack.onPingRetListener(pingResult);
                        }
                    });
                }
            });
        }
        mThreadPool.stopAddTask();
        try{
            mThreadPool.waitAllTaskComplete();
        }
        catch(InterruptedException e){
            LogUtil.logInfo("Wait all task complete error"," InterruptedException");
        }
        LogUtil.logInfo("","All task complete");
    }

    public void setPingCallBack(OnPingCallBack fun) {
        this.mPingCallBack = fun;
    }

}
