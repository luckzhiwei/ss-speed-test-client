package com.vecent.ssspeedtest;

import android.app.Application;
import android.os.Build;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.privoxy.PrivoxySetting;
import com.vecent.ssspeedtest.util.Constant;

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


}
