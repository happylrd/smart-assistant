package io.happylrd.smartassistant.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.happylrd.smartassistant.MainActivity;
import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.utils.ShareUtils;
import io.happylrd.smartassistant.utils.StaticClass;
import io.happylrd.smartassistant.utils.UtilTools;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 2000;

    private TextView splashText;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirstRun()) {
                        startActivity(new Intent(
                                SplashActivity.this, GuideActivity.class)
                        );
                    } else {
                        startActivity(new Intent(
                                SplashActivity.this, MainActivity.class)
                        );
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, DELAY_MILLIS);

        splashText = (TextView) findViewById(R.id.tv_splash);

        UtilTools.setFont(this, splashText);
    }

    private boolean isFirstRun() {
        boolean isFirst = ShareUtils
                .getBoolean(this, StaticClass.SHARE_IS_FIRST_RUN, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST_RUN, false);
            return true;
        } else {
            return false;
        }
    }

    // forbid the return key
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
