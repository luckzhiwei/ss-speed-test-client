package com.vecent.ssspeedtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.aidl.ITestFinishListener;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.List;


/**
 * Created by zhiwei on 2017/11/13.
 */

public class SpeedTestService extends Service {


    private ISpeedTestInterfaceImpl iSpeedTestInterfaceImpl;
    private Servers servers2Test;
    private GuradSpeedTester mGuradSpeedTester;

    public class ISpeedTestInterfaceImpl extends ISpeedTestInterface.Stub {

        @Override
        public void startTest(ITestFinishListener listener) throws RemoteException {
            startSpeedTest(listener);
        }

        @Override
        public void stopTest() throws RemoteException {
            stopSpeedTest();
        }

        @Override
        public boolean isTestRuning() throws RemoteException {
            if (mGuradSpeedTester != null)
                return mGuradSpeedTester.isRunning();
            else
                return false;
        }

        @Override
        public void setAllowTestRunning(boolean flag) throws RemoteException {
            if (mGuradSpeedTester != null)
                mGuradSpeedTester.setAllowRunning(flag);
        }

        @Override
        public boolean getAllowTestRuning() throws RemoteException {
            if (mGuradSpeedTester != null)
                return mGuradSpeedTester.getAllowRunning();
            else
                return false;
        }


        @Override
        public void setOnTestFinishListener(ITestFinishListener listener) throws RemoteException {
            mGuradSpeedTester.setTestFinishListener(listener);
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (iSpeedTestInterfaceImpl == null) {
            iSpeedTestInterfaceImpl = new ISpeedTestInterfaceImpl();
        }
        return iSpeedTestInterfaceImpl;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.logDebug(getClass().getName(), "on destory");
    }

    private void startSpeedTest(ITestFinishListener listener) {
        if (servers2Test == null) {
            servers2Test = new Servers(getApplicationContext(), "alexa.json");
        }
        if (mGuradSpeedTester == null) {
            mGuradSpeedTester = new GuradSpeedTester(servers2Test.getServers(), getApplicationContext());
            if (listener != null)
                mGuradSpeedTester.setTestFinishListener(listener);
            mGuradSpeedTester.start();
        } else {
            if (!mGuradSpeedTester.isRunning())
                mGuradSpeedTester.interrupt();
        }
    }

    private void stopSpeedTest() {
        if (mGuradSpeedTester != null)
            mGuradSpeedTester.exit();
    }


}
