package io.happylrd.smartassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.adapter.ExpressAdapter;
import io.happylrd.smartassistant.entity.ExpressData;
import io.happylrd.smartassistant.utils.LogUtil;
import io.happylrd.smartassistant.utils.StaticClass;

public class ExpressActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button mQueryInfoBtn;
    private ListView mListView;

    private List<ExpressData> mExpressDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        mQueryInfoBtn = (Button) findViewById(R.id.btn_query_info);
        mListView = (ListView) findViewById(R.id.listView);

        mQueryInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query_info:

                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                String url = "http://v.juhe.cn/exp/index?key=" +
                        StaticClass.JUHE_EXPRESS_APP_KEY + "&com=" + name + "&no=" + number;

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
//                            Toast.makeText(ExpressActivity.this, t, Toast.LENGTH_SHORT).show();
                            LogUtil.i("Json:" + t);

                            parsingJson(t);
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject resultObj = jsonObject.getJSONObject("result");
            JSONArray jsonArray = resultObj.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObj = (JSONObject) jsonArray.get(i);

                ExpressData expressData = new ExpressData();
                expressData.setRemark(itemObj.getString("remark"));
                expressData.setZone(itemObj.getString("zone"));
                expressData.setDateTime(itemObj.getString("datetime"));

                mExpressDataList.add(expressData);
            }

            Collections.reverse(mExpressDataList);

            ExpressAdapter adapter = new ExpressAdapter(this, mExpressDataList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
