package com.vecent.ssspeedtest.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.ProxyGuradProcess;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.net.INetImplWithProxy;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhiwei on 2017/11/20.
 */

public class GuradSpeedTester extends Thread {

    private DaoSession daoSession;

    private Handler mHandler;

    private List<TotalSpeedTestResult> results;

    private Iterator<SSServer> mIterator;

    private List<Server> servers2Test;

    private Context mContext;

    public GuradSpeedTester(List<Server> servers2Test, Context context) {
        this.daoSession = DaoManager.getInstance(context.getApplicationContext()).getDaoSession();
        List<SSServer> proxyServers = daoSession.getSSServerDao().loadAll();
        mIterator = proxyServers.iterator();
        this.servers2Test = servers2Test;
        this.mContext = context;
        this.results = new ArrayList<>();
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper());
        runTest();
        Looper.loop();
    }


    public void runTest() {
        LogUtil.logDebug(getClass().getName(), mIterator.hasNext() + "");
        if (mIterator.hasNext()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.logDebug(getClass().getName(), "start test");
                    final TotalSpeedTestResult curResult = new TotalSpeedTestResult();
                    SSServer proxySSServer = mIterator.next();
                    final ProxyGuradProcess proxyGuradProcess = new ProxyGuradProcess(proxySSServer, mContext);
                    proxyGuradProcess.start();
                    SpeedTest speedTest = new SpeedTest(servers2Test, mHandler);
                    speedTest.setRequestCallBack(new SpeedTest.RequestCallBack() {
                        @Override
                        public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                            results.add(curResult);
                            proxyGuradProcess.destory();
                            LogUtil.logDebug(getClass().getName(), "finish and next");
                            runTest();
                        }

                        @Override
                        public void onOneRequestFinishListener(SpeedTestResult result) {
                            LogUtil.logDebug(getClass().getName(), "call back " + result.getRequestServer());
                            curResult.addResult2List(result);
                        }
                    });
                    speedTest.startTest(new INetImplWithProxy());
                }
            }).start();
        } else {
            LogUtil.logDebug(getClass().getName(), "finish all");
        }
    }


    public List<TotalSpeedTestResult> getResult() {
        return this.results;
    }
}
