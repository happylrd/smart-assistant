package io.happylrd.smartassistant.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.MyUser;
import io.happylrd.smartassistant.ui.ExpressActivity;
import io.happylrd.smartassistant.ui.LoginActivity;
import io.happylrd.smartassistant.ui.PhoneAttributionActivity;
import io.happylrd.smartassistant.utils.UtilTools;
import io.happylrd.smartassistant.view.CustomDialog;

public class UserFragment extends Fragment implements View.OnClickListener {

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 101;
    public static final int CROP_REQUEST_CODE = 102;
    private File tempFile = null;

    private Button mEditUserBtn;
    private Button mChangeUserInfoBtn;
    private Button mExitUserBtn;

    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_description;

    private CircleImageView civ_profile;
    private CustomDialog mPhotoDialog;
    private Button mCameraBtn;
    private Button mGalleryBtn;
    private Button mCancelBtn;

    private TextView mExpressText;
    private TextView mPhoneAttributionText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mEditUserBtn = (Button) view.findViewById(R.id.btn_edit_user);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_description = (EditText) view.findViewById(R.id.et_description);
        mExitUserBtn = (Button) view.findViewById(R.id.btn_exit_user);
        mChangeUserInfoBtn = (Button) view.findViewById(R.id.btn_change_user_info);
        civ_profile = (CircleImageView) view.findViewById(R.id.civ_profile);
        mExpressText = (TextView) view.findViewById(R.id.tv_express);
        mPhoneAttributionText = (TextView) view.findViewById(R.id.tv_phone_attribution);

        mEditUserBtn.setOnClickListener(this);
        mChangeUserInfoBtn.setOnClickListener(this);
        mExitUserBtn.setOnClickListener(this);
        civ_profile.setOnClickListener(this);
        mExpressText.setOnClickListener(this);
        mPhoneAttributionText.setOnClickListener(this);

        UtilTools.getImageFromShare(getActivity(), civ_profile);

        //TODO:dialog's width and height will be replaced later
        mPhotoDialog = new CustomDialog(getActivity(), 0, 0, R.layout.dialog_photo,
                R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        mPhotoDialog.setCancelable(false);

        mCameraBtn = (Button) mPhotoDialog.findViewById(R.id.btn_camera);
        mGalleryBtn = (Button) mPhotoDialog.findViewById(R.id.btn_gallery);
        mCancelBtn = (Button) mPhotoDialog.findViewById(R.id.btn_cancel);
        mCameraBtn.setOnClickListener(this);
        mGalleryBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);

        setAllEnabled(false);

        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.getSex() ? "男" : "女");
        et_age.setText(userInfo.getAge() + "");
        et_description.setText(userInfo.getDescription());
    }

    private void setAllEnabled(boolean enabled) {
        et_username.setEnabled(enabled);
        et_sex.setEnabled(enabled);
        et_age.setEnabled(enabled);
        et_description.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                // 清除缓存用户对象
                MyUser.logOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_edit_user:
                setAllEnabled(true);
                mChangeUserInfoBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_change_user_info:
                String username = et_username.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String description = et_description.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(sex)
                        && !TextUtils.isEmpty(age)) {
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setSex(sex.equals("男"));
                    user.setAge(Integer.valueOf(age));

                    if (!TextUtils.isEmpty(description)) {
                        user.setDescription(description);
                    } else {
                        user.setDescription("这个人很懒，什么都没有留下。");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setAllEnabled(false);
                                mChangeUserInfoBtn.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.civ_profile:
                mPhotoDialog.show();
                break;
            case R.id.btn_camera:
                doCamera();
                break;
            case R.id.btn_gallery:
                doGallery();
                break;
            case R.id.btn_cancel:
                mPhotoDialog.dismiss();
                break;
            case R.id.tv_express:
                startActivity(new Intent(getActivity(), ExpressActivity.class));
                break;
            case R.id.tv_phone_attribution:
                startActivity(new Intent(getActivity(), PhoneAttributionActivity.class));
                break;
        }
    }

    private void doCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)
        ));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        mPhotoDialog.dismiss();
    }

    private void doGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
        mPhotoDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case GALLERY_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CROP_REQUEST_CODE:
                    if (data != null) {
                        setImageToView(data);
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    private void startPhotoZoom(Uri uri) {
        if (uri != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 设置裁剪
            intent.putExtra("crop", "true");
            // 裁剪宽高比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // 裁剪图片的质量
            intent.putExtra("outputX", 320);
            intent.putExtra("outputY", 320);
            // 发送数据
            intent.putExtra("return-data", true);

            startActivityForResult(intent, CROP_REQUEST_CODE);
        }
    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            civ_profile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        UtilTools.putImageToShare(getActivity(), civ_profile);
    }
}
