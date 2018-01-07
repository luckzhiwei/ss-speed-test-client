package com.vecent.ssspeedtest.model;

import android.os.Handler;

import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.net.INet;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private List<Server> mServers2Test;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private RequestCallBack mRequestCallBack;
    private long startTime;

    public interface RequestCallBack {
        void onAllRequestFinishListener(float timeUsed, int totalReqSize);

        void onOneRequestFinishListener(SpeedTestResult result);
    }

    public SpeedTest(List<Server> serversForTest, Handler handler) {
        this.mServers2Test = serversForTest;
        mThreadPool = new ThreadPool();
        this.mHandler = handler;
    }

    public SpeedTestResult httpSpeedTest(INet net, Server server2Request) {
        return net.getHttpTestResult(server2Request);
    }

    /**
     * 策略模式的一种体现：
     * net的行为在客户端看来都是一样的,但是实现可以是有走代理和不走代理两种情况
     *
     * @param net
     */
    public void startTest(final INet net) {
        this.startTime = System.currentTimeMillis();
        for (final Server server : mServers2Test) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final SpeedTestResult requestResult = httpSpeedTest(net, server);
                    if (requestResult != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mRequestCallBack.onOneRequestFinishListener(requestResult);
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
                if (mRequestCallBack != null) {
                    mRequestCallBack.onAllRequestFinishListener(1.0f * (endTime - startTime) / 1000, mServers2Test.size());
                }
            }
        });
    }


    public void setRequestCallBack(RequestCallBack callback) {
        this.mRequestCallBack = callback;
    }

    public void cancel() {
        this.mThreadPool.stopNow();
    }

    public long getTimeStart() {
        return startTime;
    }


}
