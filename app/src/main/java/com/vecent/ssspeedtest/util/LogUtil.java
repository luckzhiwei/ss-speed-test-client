package com.vecent.ssspeedtest.util;

import android.util.Log;

/**
 * Created by zhiwei on 2017/9/5.
 */

public class LogUtil {

    private static final int LOG_ERROR_TAG = 1;
    private static final int LOG_DEBUG_TAG = 1;
    private static final int LOG_INFO_TAG = 1;
    public static final int LOG_VERBASE_TAG = 1;

    public static void logError(String tagPostfix, String msg) {
        if (LOG_ERROR_TAG != 0) {
            Log.e(Constant.LOG_TAG + tagPostfix, msg);
        }
    }

    public static void logInfo(String tagPostfix, String msg) {
        if (LOG_INFO_TAG != 0) {
            Log.i(Constant.LOG_TAG + tagPostfix, msg);
        }
    }

    public static void logVerbase(String tagPostfix, String msg) {
        if (LOG_VERBASE_TAG != 0) {
            Log.v(Constant.LOG_TAG + tagPostfix, msg);
        }
    }

    public static void logDebug(String tagPostfix, String msg) {
        if (LOG_DEBUG_TAG != 0) {
            Log.i(Constant.LOG_TAG + tagPostfix, msg);
        }
    }
}
