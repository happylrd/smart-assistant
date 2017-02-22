package io.happylrd.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.WeChatData;

public class WeChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private List<WeChatData> mDataList;
    private WeChatData mData;

    public WeChatAdapter(Context context, List<WeChatData> dataList) {
        this.mContext = context;
        this.mDataList = dataList;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = inflater.inflate(R.layout.wechat_item, null);

            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.mTitleText = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mSourceText = (TextView) convertView.findViewById(R.id.tv_source);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mData = mDataList.get(position);
        viewHolder.mTitleText.setText(mData.getTitle());
        viewHolder.mSourceText.setText(mData.getSource());

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_image;
        private TextView mTitleText;
        private TextView mSourceText;
    }
}
