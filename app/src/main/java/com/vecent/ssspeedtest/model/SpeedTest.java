package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;

import android.os.Handler;

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
                    LogUtil.logDebug(getClass().getName(), pingResult.getRequestServer());
                    LogUtil.logDebug(getClass().getName(), pingResult.getTotalSize() + "");
                    LogUtil.logDebug(getClass().getName(), pingResult.getStatusCode() + "");
                    LogUtil.logDebug(getClass().getName(), pingResult.getTimeUsed() + "");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPingCallBack.onPingRetListener(pingResult);
                        }
                    });
                }
            });
        }
    }

    public void setPingCallBack(OnPingCallBack fun) {
        this.mPingCallBack = fun;
    }

}
