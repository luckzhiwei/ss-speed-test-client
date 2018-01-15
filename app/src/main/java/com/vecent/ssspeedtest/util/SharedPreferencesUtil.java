package com.vecent.ssspeedtest.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhiwei on 2018/1/15.
 */

public class SharedPreferencesUtil {

    public static void storeLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getEidtor(context);
        editor.putLong(key, value);
        editor.commit();
    }

    public static void storeBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEidtor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }


    private static SharedPreferences.Editor getEidtor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", 0);
        return sharedPreferences.edit();
    }

}
