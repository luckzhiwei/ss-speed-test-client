package com.vecent.ssspeedtest.service;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.vecent.ssspeedtest.aidl.ITestFinishListener;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.guradprocess.PrivoxyGuradProcess;
import com.vecent.ssspeedtest.model.guradprocess.SSProxyGuradProcess;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.net.INetImplDefault;
import com.vecent.ssspeedtest.model.net.INetImplWithPrivoxy;
import com.vecent.ssspeedtest.model.net.INetImplWithProxy;
import com.vecent.ssspeedtest.model.net.INetImplWithSSProxy;
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

    private PrivoxyGuradProcess mPrivoxyGuradProcess;


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
        if (Build.VERSION.SDK_INT < 24) {
            mPrivoxyGuradProcess = new PrivoxyGuradProcess(mContext, Constant.BACK_PRIVOXY_CONFIG_FILE_NAME);
            mPrivoxyGuradProcess.start();
        }
        Looper.loop();
    }


    public void runTest() {
        if (mIterator.hasNext()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final TotalSpeedTestResult curResult = new TotalSpeedTestResult();
                    final SSServer proxySSServer = mIterator.next();
                    final SSProxyGuradProcess proxyGuradProcess = new SSProxyGuradProcess(proxySSServer, mContext, Constant.SOCKS_SERVER_LOCAL_PORT_BACK);
                    if (!proxySSServer.isSystemProxy()) {
                        proxyGuradProcess.start();
                    }
                    curResult.setServer2TestAddr(proxySSServer.getServerAddr());
                    SpeedTest speedTest = new SpeedTest(servers2Test, mHandler);
                    speedTest.setRequestCallBack(new SpeedTest.RequestCallBack() {
                        @Override
                        public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                            results.add(curResult);
                            if (!proxySSServer.isSystemProxy())
                                proxyGuradProcess.destory();
                            runTest();
                        }

                        @Override
                        public void onOneRequestFinishListener(SpeedTestResult result) {
                            curResult.addResult2List(result);
                        }
                    });
                    if (proxySSServer.isSystemProxy()) {
                        speedTest.startTest(new INetImplDefault());
                    } else {
                        if (Build.VERSION.SDK_INT < 24) {
                            speedTest.startTest(new INetImplWithPrivoxy(Constant.PRIVOXY_LOCAL_PORT_BACK));
                        } else if (proxySSServer.isSystemProxy()) {
                            speedTest.startTest(new INetImplWithSSProxy(Constant.SOCKS_SERVER_LOCAL_PORT_BACK));
                        }
                    }
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
                writeResult2DB();
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

    private void rmDBCached() {
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
