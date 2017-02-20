package io.happylrd.smartassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.MyUser;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_mail;
    private Button mForgetPasswordBtn;

    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_password_again;

    private Button mChangePasswordBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView() {
        et_mail = (EditText) findViewById(R.id.et_mail);
        mForgetPasswordBtn = (Button) findViewById(R.id.btn_forget_password);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_password_again = (EditText) findViewById(R.id.et_new_password_again);
        mChangePasswordBtn = (Button) findViewById(R.id.btn_change_password);

        mForgetPasswordBtn.setOnClickListener(this);
        mChangePasswordBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                final String mail = et_mail.getText().toString().trim();
                if (!TextUtils.isEmpty(mail)) {
                    MyUser.resetPasswordByEmail(mail, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱已经发送至：" + mail, Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱发送失败", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.btn_change_password:
                String oldPassword = et_old_password.getText().toString().trim();
                String newPassword = et_new_password.getText().toString().trim();
                String newPasswordAgain = et_new_password_again.getText().toString().trim();

                if (!TextUtils.isEmpty(oldPassword) &&
                        !TextUtils.isEmpty(newPassword) &&
                        !TextUtils.isEmpty(newPasswordAgain)) {

                    if (newPassword.equals(newPasswordAgain)) {
                        MyUser.updateCurrentUserPassword(oldPassword, newPassword,
                                new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(ForgetPasswordActivity.this,
                                                    "重置密码成功", Toast.LENGTH_SHORT)
                                                    .show();
                                            finish();
                                        } else {
                                            Toast.makeText(ForgetPasswordActivity.this,
                                                    "重置密码失败", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
}
