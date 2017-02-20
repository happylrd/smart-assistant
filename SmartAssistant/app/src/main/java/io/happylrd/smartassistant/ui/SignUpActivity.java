package io.happylrd.smartassistant.ui;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.MyUser;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private EditText et_password_again;
    private EditText et_mail;

    private RadioGroup mRadioGroup;
    private EditText et_age;
    private EditText et_description;

    private Button mSignUpBtn;

    private boolean isMale = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_again = (EditText) findViewById(R.id.et_password_again);
        et_mail = (EditText) findViewById(R.id.et_mail);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        et_age = (EditText) findViewById(R.id.et_age);
        et_description = (EditText) findViewById(R.id.et_description);

        mSignUpBtn = (Button) findViewById(R.id.btn_sign_up);

        mSignUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String passwordAgain = et_password_again.getText().toString().trim();
                String mail = et_mail.getText().toString().trim();

                String age = et_age.getText().toString().trim();
                String description = et_description.getText().toString().trim();

                if(!TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(passwordAgain) &&
                        !TextUtils.isEmpty(mail) &&
                        !TextUtils.isEmpty(age)){

                    if(password.equals(passwordAgain)){
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId == R.id.rb_boy){
                                    isMale = true;
                                }else if(checkedId == R.id.rb_girl){
                                    isMale = false;
                                }
                            }
                        });

                        if(TextUtils.isEmpty(description)){
                            description = "这个人很懒，什么都没有留下。";
                        }

                        MyUser user = new MyUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(mail);

                        user.setSex(isMale);
                        user.setAge(Integer.valueOf(age));
                        user.setDescription(description);
                        
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e == null){
                                    Toast.makeText(SignUpActivity.this,
                                            "注册成功", Toast.LENGTH_SHORT)
                                            .show();
                                    finish();
                                }else{
                                    Toast.makeText(SignUpActivity.this,
                                            "注册失败: " + e.toString(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT)
                                .show();
                    }
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
            break;
        }
    }
}
