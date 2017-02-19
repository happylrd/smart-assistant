package io.happylrd.smartassistant.utils;

import android.util.Log;

public class LogUtil {

    private static final boolean LOG_DEBUG_MODE = true;

    public static final String TAG = "SmartAssistant";

    public static void v(String msg) {
        if (LOG_DEBUG_MODE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (LOG_DEBUG_MODE) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (LOG_DEBUG_MODE) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (LOG_DEBUG_MODE) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (LOG_DEBUG_MODE) {
            Log.e(TAG, msg);
        }
    }
}
