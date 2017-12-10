package com.vecent.ssspeedtest;

import android.app.Application;
import android.os.Build;

import com.vecent.ssspeedtest.model.privoxy.PrivoxySetting;

/**
 * Created by zhiwei on 2017/12/8.
 */

public class MyApplication extends Application {

    private PrivoxySetting mPrivoxySetting;

    @Override
    public void onCreate() {
        super.onCreate();
        initPrivoxySetting();
    }

    private void initPrivoxySetting() {
        if (Build.VERSION.SDK_INT >= 24) {
            return;
        }
        mPrivoxySetting = new PrivoxySetting(this);
        mPrivoxySetting.install();
    }
}
