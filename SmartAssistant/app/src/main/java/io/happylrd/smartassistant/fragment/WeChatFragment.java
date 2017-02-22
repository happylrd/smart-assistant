package io.happylrd.smartassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.adapter.WeChatAdapter;
import io.happylrd.smartassistant.entity.WeChatData;
import io.happylrd.smartassistant.ui.NewsDetailActivity;
import io.happylrd.smartassistant.utils.LogUtil;
import io.happylrd.smartassistant.utils.StaticClass;

public class WeChatFragment extends Fragment {

    private ListView mListView;

    private List<WeChatData> mDataList = new ArrayList<>();

    private List<String> mTitleList = new ArrayList<>();
    private List<String> mNewsUrlList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_we_chat, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.i("position:" + position);

                Intent intent = NewsDetailActivity.newIntent(getActivity(),
                        mTitleList.get(position), mNewsUrlList.get(position));

                startActivity(intent);
            }
        });

        String url = "http://v.juhe.cn/weixin/query?key=" +
                StaticClass.JUHE_WECHAT_APP_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtil.i("Json:" + t);

                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject resultObj = jsonObject.getJSONObject("result");
            JSONArray jsonList = resultObj.getJSONArray("list");

            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject jsonObj = jsonList.getJSONObject(i);

                WeChatData data = new WeChatData();
                String title = jsonObj.getString("title");
                String url = jsonObj.getString("url");
                data.setTitle(title);
                data.setSource(jsonObj.getString("source"));
                data.setImgUrl(jsonObj.getString("firstImg"));

                mDataList.add(data);

                mTitleList.add(title);
                mNewsUrlList.add(url);
            }

            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mDataList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
