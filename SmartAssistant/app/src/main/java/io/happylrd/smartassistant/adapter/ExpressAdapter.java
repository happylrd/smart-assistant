package io.happylrd.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.entity.ExpressData;

public class ExpressAdapter extends BaseAdapter {

    private Context mContext;
    private List<ExpressData> mExpressDataList;

    private LayoutInflater inflater;

    private ExpressData mExpressData;

    public ExpressAdapter(Context context, List<ExpressData> expressDataList) {
        this.mContext = context;
        this.mExpressDataList = expressDataList;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mExpressDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mExpressDataList.get(position);
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
            convertView = inflater.inflate(R.layout.express_item, null);

            viewHolder.mRemarkText = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.mZoneText = (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.mDateTimeText = (TextView) convertView.findViewById(R.id.tv_dateTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mExpressData = mExpressDataList.get(position);

        viewHolder.mRemarkText.setText(mExpressData.getRemark());
        viewHolder.mZoneText.setText(mExpressData.getZone());
        viewHolder.mDateTimeText.setText(mExpressData.getDateTime());

        return convertView;
    }

    class ViewHolder {
        private TextView mRemarkText;
        private TextView mZoneText;
        private TextView mDateTimeText;
    }
}
