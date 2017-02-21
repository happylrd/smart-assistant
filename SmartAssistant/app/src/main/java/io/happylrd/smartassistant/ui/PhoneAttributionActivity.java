package io.happylrd.smartassistant.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.utils.LogUtil;
import io.happylrd.smartassistant.utils.StaticClass;

public class PhoneAttributionActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_number;
    private ImageView mCompanyImage;
    private TextView mResultText;

    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9,
            btn_del, btn_query;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_attribution);

        initView();
    }

    private void initView() {
        et_number = (EditText) findViewById(R.id.et_number);
        mCompanyImage = (ImageView) findViewById(R.id.iv_company);
        mResultText = (TextView) findViewById(R.id.tv_result);

        btn_0 = (Button) findViewById(R.id.btn_zero);
        btn_1 = (Button) findViewById(R.id.btn_one);
        btn_2 = (Button) findViewById(R.id.btn_two);
        btn_3 = (Button) findViewById(R.id.btn_three);
        btn_4 = (Button) findViewById(R.id.btn_four);
        btn_5 = (Button) findViewById(R.id.btn_five);
        btn_6 = (Button) findViewById(R.id.btn_six);
        btn_7 = (Button) findViewById(R.id.btn_seven);
        btn_8 = (Button) findViewById(R.id.btn_eight);
        btn_9 = (Button) findViewById(R.id.btn_nine);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_query = (Button) findViewById(R.id.btn_query);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        String str = et_number.getText().toString();

        switch (v.getId()) {
            case R.id.btn_zero:
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
                if (flag) {
                    str = "";
                    et_number.setText("");
                    flag = false;
                }
                et_number.setText(str + ((Button) v).getText());
                et_number.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    et_number.setText(str.substring(0, str.length() - 1));
                    et_number.setSelection(str.length() - 1);
                }
                break;
            case R.id.btn_query:
                if (!TextUtils.isEmpty(str)) {
                    getPhoneAttribution(str);
                }
                break;
        }
    }

    private void getPhoneAttribution(String phoneNumStr) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + phoneNumStr +
                "&key=" + StaticClass.JUHE_PHONE_ATTRIBUTION_APP_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtil.i("phoneAttribution:" + t);

                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject resultObj = jsonObject.getJSONObject("result");
            String province = resultObj.getString("province");
            String city = resultObj.getString("city");
            String areacode = resultObj.getString("areacode");
            String zip = resultObj.getString("zip");
            String company = resultObj.getString("company");
            String card = resultObj.getString("card");

            mResultText.setText("归属地:" + province + city + "\n"
                    + "区号:" + areacode + "\n"
                    + "邮编:" + zip + "\n"
                    + "运营商:" + company + "\n"
                    + "类型:" + card);

            switch (company) {
                case "移动":
                    mCompanyImage.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    mCompanyImage.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    mCompanyImage.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }

            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
