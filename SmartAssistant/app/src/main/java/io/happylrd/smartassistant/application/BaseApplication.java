package io.happylrd.smartassistant.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import io.happylrd.smartassistant.utils.StaticClass;

public class BaseApplication extends Application {

    private static final boolean BUGLY_DEBUG_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(),
                StaticClass.BUGLY_APP_ID, BUGLY_DEBUG_MODE);
    }
}
