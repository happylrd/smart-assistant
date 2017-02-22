package io.happylrd.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.ChatData;

public class ChatListAdapter extends BaseAdapter {

    public static final int LEFT_INFO_TYPE = 1;
    public static final int RIGHT_INFO_TYPE = 2;

    private Context mContext;
    private LayoutInflater inflater;

    private List<ChatData> mChatDataList;

    public ChatListAdapter(Context context, List<ChatData> chatDataList) {
        this.mContext = context;
        this.mChatDataList = chatDataList;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mChatDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftInfoViewHolder leftInfoViewHolder = null;
        RightInfoViewHolder rightInfoViewHolder = null;

        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case LEFT_INFO_TYPE:
                    leftInfoViewHolder = new LeftInfoViewHolder();
                    convertView = inflater.inflate(R.layout.left_item, null);
                    leftInfoViewHolder.mLeftInfoText = (TextView)
                            convertView.findViewById(R.id.tv_left_info);

                    convertView.setTag(leftInfoViewHolder);
                    break;
                case RIGHT_INFO_TYPE:
                    rightInfoViewHolder = new RightInfoViewHolder();
                    convertView = inflater.inflate(R.layout.right_item, null);
                    rightInfoViewHolder.mRightInfoText = (TextView)
                            convertView.findViewById(R.id.tv_right_info);

                    convertView.setTag(rightInfoViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case LEFT_INFO_TYPE:
                    leftInfoViewHolder = (LeftInfoViewHolder) convertView.getTag();
                    break;
                case RIGHT_INFO_TYPE:
                    rightInfoViewHolder = (RightInfoViewHolder) convertView.getTag();
                    break;
            }
        }

        ChatData data = mChatDataList.get(position);
        switch (type) {
            case LEFT_INFO_TYPE:
                leftInfoViewHolder.mLeftInfoText.setText(data.getInfo());
                break;
            case RIGHT_INFO_TYPE:
                rightInfoViewHolder.mRightInfoText.setText(data.getInfo());
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        ChatData data = mChatDataList.get(position);
        return data.getType();
    }

    @Override
    public int getViewTypeCount() {
        // TODO:mChatDataList.size() + 1;
        return 3;
    }

    private class LeftInfoViewHolder {
        private TextView mLeftInfoText;
    }

    private class RightInfoViewHolder {
        private TextView mRightInfoText;
    }
}
