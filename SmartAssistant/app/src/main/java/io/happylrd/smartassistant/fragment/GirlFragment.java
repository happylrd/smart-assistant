package io.happylrd.smartassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.adapter.GridAdapter;
import io.happylrd.smartassistant.entity.GirlData;
import io.happylrd.smartassistant.utils.LogUtil;
import io.happylrd.smartassistant.utils.PicassoUtils;
import io.happylrd.smartassistant.utils.StaticClass;
import io.happylrd.smartassistant.view.CustomDialog;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFragment extends Fragment {

    private GridView mGridView;
    private List<GirlData> mDataList = new ArrayList<>();
    private GridAdapter mAdapter;

    private CustomDialog mGirlDialog;
    private ImageView mGirlImage;
    private List<String> mUrlList = new ArrayList<>();
    private PhotoViewAttacher mAttacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mGridView = (GridView) view.findViewById(R.id.gridView);

        mGirlDialog = new CustomDialog(getActivity(), 0, 0, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        mGirlImage = (ImageView) mGirlDialog.findViewById(R.id.iv_girl);

        RxVolley.get(StaticClass.GANK_GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtil.i("Json:" + t);

                parsingJson(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtils.loadImageView(getActivity(),
                        mUrlList.get(position), mGirlImage);
                mAttacher = new PhotoViewAttacher(mGirlImage);
                mAttacher.update();
                mGirlDialog.show();
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String imgUrl = jsonObj.getString("url");
                mUrlList.add(imgUrl);

                GirlData data = new GirlData();
                data.setImgUrl(imgUrl);

                mDataList.add(data);
            }

            mAdapter = new GridAdapter(getActivity(), mDataList);
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
