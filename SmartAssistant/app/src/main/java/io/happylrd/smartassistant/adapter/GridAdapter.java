package io.happylrd.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.GirlData;
import io.happylrd.smartassistant.utils.PicassoUtils;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<GirlData> mDataList;
    private GirlData mData;

    private WindowManager windowManager;
    private int width;

    public GridAdapter(Context context, List<GirlData> dataList) {
        this.mContext = context;
        this.mDataList = dataList;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        //TODO:need to be modified later
        width = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.mGirlImage = (ImageView) convertView.findViewById(R.id.iv_girl);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mData = mDataList.get(position);
        String url = mData.getImgUrl();

        PicassoUtils.loadImageViewSize(mContext, url, width / 2, 500, viewHolder.mGirlImage);

        return convertView;
    }

    private class ViewHolder {
        private ImageView mGirlImage;
    }
}
