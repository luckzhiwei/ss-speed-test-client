package com.vecent.ssspeedtest.dao;

import android.content.Context;

import com.vecent.ssspeedtest.MainActivity;
import com.vecent.ssspeedtest.greendao.DaoMaster;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.util.LogUtil;

/**
 * Created by zhiwei on 2017/11/7.
 */

public class DaoManager {

    private DaoMaster daoMaster;
    private static volatile DaoManager mInstance = null;

    private DaoManager(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new
                DaoMaster.DevOpenHelper(context, "user.db");
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
    }

    public static DaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DaoManager.class) {
                if (mInstance == null) {
                    LogUtil.logDebug("speedtest", "initial the dao manager");
                    mInstance = new DaoManager(context);
                }
            }
        }
        return mInstance;
    }

    public DaoSession getDaoSession() {
        return daoMaster.newSession();
    }


}
