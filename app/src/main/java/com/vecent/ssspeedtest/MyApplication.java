package com.vecent.ssspeedtest;

import android.app.Application;
import android.os.Build;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.privoxy.PrivoxySetting;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by zhiwei on 2017/12/8.
 */

public class MyApplication extends Application {

    private PrivoxySetting mPrivoxySetting;

    @Override
    public void onCreate() {
        super.onCreate();
        initPrivoxySetting();
        initSSList();
        initExceptionHandler();
    }


    private void initPrivoxySetting() {
        if (Build.VERSION.SDK_INT >= 24) {
            return;
        }
        mPrivoxySetting = new PrivoxySetting(getApplicationContext());
        mPrivoxySetting.install();
    }

    private void initSSList() {
        DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
        List<SSServer> ret = daoSession.getSSServerDao().loadAll();
        if (ret.size() == 0) {
            SSServer server = new SSServer();
            server.setServerAddr(Constant.SYSTEM_PROXY);
            server.setServerPort(0);
            server.setMethod(Constant.SYSTEM_PROXY);
            server.setPassword(Constant.SYSTEM_PROXY);
            daoSession.getSSServerDao().insert(server);
        }
    }

    private void initExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }

    private class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            recordException(throwable);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(-1);
        }

        private void recordException(Throwable ex) {
            if (ex == null) return;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte content[] = baos.toByteArray();
            LogUtil.logError(getClass().getName(), new String(content));
        }
    }


}
