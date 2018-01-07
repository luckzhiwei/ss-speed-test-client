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
import com.vecent.ssspeedtest.model.net.INetImplWithSSProxy;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;
import com.vecent.ssspeedtest.util.NetWorkUtil;

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

    private boolean isRunning;

    private int grade = 0;

    private boolean allowRunning = true;

    private boolean onlyInWifiRunning = true;

    private boolean isRealRunning = true;


    public GuradSpeedTester(List<Server> servers2Test, Context context) {
        this.servers2Test = servers2Test;
        this.mContext = context;
        this.results = new ArrayList<>();
        this.isRunning = false;
    }

    @Override
    public void run() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        mHandler = new Handler(Looper.myLooper());
        if (Build.VERSION.SDK_INT < 24) {
            mPrivoxyGuradProcess = new PrivoxyGuradProcess(mContext, Constant.BACK_PRIVOXY_CONFIG_FILE_NAME);
            mPrivoxyGuradProcess.start();
            mPrivoxyGuradProcess.waitFor(Constant.PRIVOXY_LOCAL_PORT_BACK);
        }
        startTest();
        Looper.loop();
    }


    public void runTest() {
        this.isRealRunning = checkRunningCondition();
        if (isRealRunning) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    grade = 0;
                    final TotalSpeedTestResult curResult = new TotalSpeedTestResult();
                    final SSServer proxySSServer = mIterator.next();
                    final SSProxyGuradProcess proxyGuradProcess = new SSProxyGuradProcess(proxySSServer, mContext, Constant.SOCKS_SERVER_LOCAL_PORT_BACK);
                    if (!proxySSServer.isSystemProxy()) {
                        proxyGuradProcess.start();
                        proxyGuradProcess.waitFor(Constant.SOCKS_SERVER_LOCAL_PORT_BACK);
                    }
                    curResult.setServer2TestAddr(proxySSServer.getServerAddr());
                    SpeedTest speedTest = new SpeedTest(servers2Test, mHandler);
                    speedTest.setRequestCallBack(new SpeedTest.RequestCallBack() {
                        @Override
                        public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                            results.add(curResult);
                            if (!proxySSServer.isSystemProxy()) {
                                proxyGuradProcess.destory();
                                proxyGuradProcess.waitForClose(Constant.SOCKS_SERVER_LOCAL_PORT_BACK);
                            }
                            try {
                                if (mTestFinishListener != null) {
                                    grade = ((100 * grade) / totalReqSize);
                                    mTestFinishListener.onOneItemFinish(proxySSServer.getId(), grade);
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            runTest();
                        }

                        @Override
                        public void onOneRequestFinishListener(SpeedTestResult result) {
                            curResult.addResult2List(result);
                            if (!result.isExceptionOccured())
                                grade++;
                        }
                    });
                    if (proxySSServer.isSystemProxy()) {
                        speedTest.startTest(new INetImplDefault());
                    } else {
                        if (Build.VERSION.SDK_INT < 24) {
                            speedTest.startTest(new INetImplWithPrivoxy(Constant.PRIVOXY_LOCAL_PORT_BACK));
                        } else {
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

    private void startTest() {
        readSSProxyServerFromDB();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.logDebug(getClass().getName(), "start test");
                runTest();
            }
        });
        if (mTestFinishListener != null) {
            try {
                mTestFinishListener.onTestStart();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private void finishSpeedTest() {
        try {
            if (mTestFinishListener != null) {
                mTestFinishListener.onTestFinish(isRealRunning);
            }
            this.results = new ArrayList<>();
            this.isRunning = false;
            Thread.sleep(Constant.SERVICE_WAIT_INTERNAL);
            startTest();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            LogUtil.logDebug(getClass().getName(), "interupted");
            startTest();
            e.printStackTrace();
        }
    }

    private void readSSProxyServerFromDB() {
        DaoSession daoSession = DaoManager.getInstance(mContext.getApplicationContext()).getDaoSession();
        List<SSServer> proxyServers = daoSession.getSSServerDao().loadAll();
        mIterator = proxyServers.iterator();
    }


    public List<TotalSpeedTestResult> getResult() {
        return this.results;
    }

    public void setTestFinishListener(ITestFinishListener listener) {
        this.mTestFinishListener = listener;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void exit() {
        mHandler.getLooper().quit();
    }

    public void setAllowRunning(boolean flag) {
        this.allowRunning = flag;
    }

    public boolean getAllowRunning() {
        return this.allowRunning;
    }

    public void setOnlyWifiTest(boolean tag) {
        this.onlyInWifiRunning = tag;
    }

    public boolean getOnlyWifiTest() {
        return this.onlyInWifiRunning;
    }

    private boolean checkRunningCondition() {
        if (!mIterator.hasNext())
            return false;
        if (!allowRunning)
            return false;
        if (onlyInWifiRunning) {
            if (!NetWorkUtil.isWifi(mContext)) {
                return false;
            }
        }
        return true;
    }



}
