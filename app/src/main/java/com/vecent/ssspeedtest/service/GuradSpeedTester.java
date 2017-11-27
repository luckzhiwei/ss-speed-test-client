package com.vecent.ssspeedtest.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.vecent.ssspeedtest.aidl.ITestFinishListener;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.ProxyGuradProcess;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.net.INetImplWithProxy;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhiwei on 2017/11/20.
 */

public class GuradSpeedTester extends Thread {


    private Handler mHandler;

    private List<TotalSpeedTestResult> results;

    private Iterator<SSServer> mIterator;

    private List<Server> servers2Test;

    private Context mContext;

    private ITestFinishListener mTestFinishListener;


    public GuradSpeedTester(List<Server> servers2Test, Context context) {
        this.servers2Test = servers2Test;
        this.mContext = context;
        this.results = new ArrayList<>();
    }

    @Override
    public void run() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        mHandler = new Handler(Looper.myLooper());
        LogUtil.logDebug(getClass().getName(), "start test");
        readSSProxyServerFromDB();
        runTest();
        Looper.loop();
    }


    public void runTest() {
        if (mIterator.hasNext()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final TotalSpeedTestResult curResult = new TotalSpeedTestResult();
                    SSServer proxySSServer = mIterator.next();
                    final ProxyGuradProcess proxyGuradProcess = new ProxyGuradProcess(proxySSServer, mContext, Constant.SOCKS_SERVER_LOCAL_PORT_BACK);
                    proxyGuradProcess.start();
                    curResult.setServer2TestAddr(proxySSServer.getServerAddr());
                    SpeedTest speedTest = new SpeedTest(servers2Test, mHandler);
                    speedTest.setRequestCallBack(new SpeedTest.RequestCallBack() {
                        @Override
                        public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                            results.add(curResult);
                            proxyGuradProcess.destory();
                            runTest();
                        }

                        @Override
                        public void onOneRequestFinishListener(SpeedTestResult result) {
                            curResult.addResult2List(result);
                            LogUtil.logDebug(getClass().getName(), result.isExceptionOccured() + "");
                        }
                    });
                    speedTest.startTest(new INetImplWithProxy(Constant.SOCKS_SERVER_LOCAL_PORT_BACK));
                }
            }).start();
        } else {
            LogUtil.logDebug(getClass().getName(), "end test");
            finishSpeedTest();
        }
    }


    private void finishSpeedTest() {
        try {
            rmDBCached();
            if (mTestFinishListener != null && results.size() != 0) {
                try {
                    writeResult2DB();
                } catch (Exception e) {
                    LogUtil.logDebug(getName(), e.getMessage());
                }
                mTestFinishListener.onTestFinish(results);
            }
            this.results = new ArrayList<>();
            Thread.sleep(Constant.SERVICE_WAIT_INTERNAL);
            readSSProxyServerFromDB();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LogUtil.logDebug(getClass().getName(), "start test");
                    runTest();
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readSSProxyServerFromDB() {
        DaoSession daoSession = DaoManager.getInstance(mContext.getApplicationContext()).getDaoSession();
        List<SSServer> proxyServers = daoSession.getSSServerDao().loadAll();
        mIterator = proxyServers.iterator();
    }

    private void rmDBCached(){
        DaoSession daoSession = DaoManager.getInstance(mContext.getApplicationContext()).getDaoSession();
        daoSession.getTotalSpeedTestResultDao().deleteAll();
    }

    private void writeResult2DB() {
        DaoSession daoSession = DaoManager.getInstance(mContext.getApplicationContext()).getDaoSession();
        daoSession.getTotalSpeedTestResultDao().insertInTx(results);
    }

    public List<TotalSpeedTestResult> getResult() {
        return this.results;
    }

    public void setTestFinishListener(ITestFinishListener listener) {
        this.mTestFinishListener = listener;
    }

    public void exit() {
        mHandler.getLooper().quit();
    }

}
