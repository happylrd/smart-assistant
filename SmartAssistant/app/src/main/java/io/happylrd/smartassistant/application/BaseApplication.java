package io.happylrd.smartassistant.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import io.happylrd.smartassistant.utils.StaticClass;

public class BaseApplication extends Application {

    private static final boolean BUGLY_DEBUG_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(),
                StaticClass.BUGLY_APP_ID, BUGLY_DEBUG_MODE);

        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        SpeechUtility.createUtility(getApplicationContext(),
                SpeechConstant.APPID + "=" + StaticClass.XF_APP_ID);
    }
}
