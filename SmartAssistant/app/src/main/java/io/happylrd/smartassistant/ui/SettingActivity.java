package io.happylrd.smartassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.utils.ShareUtils;
import io.happylrd.smartassistant.utils.StaticClass;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Switch mSpeakSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        mSpeakSwitch = (Switch) findViewById(R.id.switch_speak);
        mSpeakSwitch.setOnClickListener(this);

        boolean isSpeak = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_SPEAK, false);
        mSpeakSwitch.setChecked(isSpeak);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_speak:
                mSpeakSwitch.setSelected(!mSpeakSwitch.isSelected());
                ShareUtils.putBoolean(this, "isSpeak", mSpeakSwitch.isChecked());
                break;
        }
    }
}
