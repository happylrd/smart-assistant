package io.happylrd.smartassistant.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.happylrd.smartassistant.MainActivity;
import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.MyUser;
import io.happylrd.smartassistant.utils.ShareUtils;
import io.happylrd.smartassistant.view.CustomDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;

    private Button mLoginBtn;
    private Button mSignUpBtn;

    private CheckBox keep_password;

    private TextView mForgetPasswordText;

    //TODO: will be replaced with awesome dialog later
    private CustomDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mSignUpBtn = (Button) findViewById(R.id.btn_sign_up);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        mForgetPasswordText = (TextView) findViewById(R.id.tv_forget_password);

        mLoginBtn.setOnClickListener(this);
        mSignUpBtn.setOnClickListener(this);
        mForgetPasswordText.setOnClickListener(this);

        //TODO:dialog's width and height will be replaced later
        mLoadingDialog = new CustomDialog(this, 0, 0, R.layout.dialog_loading,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        mLoadingDialog.setCancelable(false);

        boolean isCheck = ShareUtils.getBoolean(this, "keepPassword", false);
        keep_password.setChecked(isCheck);
        if (isCheck) {
            et_username.setText(ShareUtils.getString(this, "username", ""));
            et_password.setText(ShareUtils.getString(this, "password", ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    mLoadingDialog.show();

                    final MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            mLoadingDialog.dismiss();

                            if (e == null) {
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(
                                            LoginActivity.this, MainActivity.class)
                                    );
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            "请前往邮箱验证", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "登录失败: " + e.toString(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.btn_sign_up:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ShareUtils.putBoolean(this, "keepPassword", keep_password.isChecked());

        if (keep_password.isChecked()) {
            ShareUtils.putString(this, "username", et_username.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.remove(this, "username");
            ShareUtils.remove(this, "password");
        }
    }
}
