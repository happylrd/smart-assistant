package io.happylrd.smartassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.adapter.ChatListAdapter;
import io.happylrd.smartassistant.entity.ChatData;
import io.happylrd.smartassistant.utils.LogUtil;
import io.happylrd.smartassistant.utils.StaticClass;

public class AssistantFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;

    private List<ChatData> mChatDataList = new ArrayList<>();
    private ChatListAdapter mAdapter;

    private EditText et_info;
    private Button mSendBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assistant, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mChatListView = (ListView) view.findViewById(R.id.chatListView);
        et_info = (EditText) view.findViewById(R.id.et_info);
        mSendBtn = (Button) view.findViewById(R.id.btn_send);

        mSendBtn.setOnClickListener(this);

        mAdapter = new ChatListAdapter(getActivity(), mChatDataList);
        mChatListView.setAdapter(mAdapter);

        addLeftItem("你好，我是小冰！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String info = et_info.getText().toString();
                if (!TextUtils.isEmpty(info)) {
                    if (info.length() > 30) {
                        Toast.makeText(getActivity(), "输入长度超出限制", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        et_info.setText("");
                        addRightItem(info);

                        String url = "http://op.juhe.cn/robot/index?info=" + info
                                + "&key=" + StaticClass.JUHE_ROBOT_APP_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                LogUtil.i("Json:" + t);
                                parsingJson(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject resultObj = jsonObject.getJSONObject("result");
            String info = resultObj.getString("text");

            addLeftItem(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeftItem(String info) {
        ChatData data = new ChatData();
        data.setType(ChatListAdapter.LEFT_INFO_TYPE);
        data.setInfo(info);
        mChatDataList.add(data);

        mAdapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void addRightItem(String info) {
        ChatData data = new ChatData();
        data.setType(ChatListAdapter.RIGHT_INFO_TYPE);
        data.setInfo(info);
        mChatDataList.add(data);

        mAdapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
