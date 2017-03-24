package com.popupwindowlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.popupwindowlibrary.R;
import com.popupwindowlibrary.bean.PopWindowInfo;

import java.util.List;

public class CouponsPopWindowAdapter extends BaseAdapter {

    private Context mContext;
    private List<PopWindowInfo> mList;

    private int ids;

    public CouponsPopWindowAdapter(Context context, List<PopWindowInfo> list, int ids) {
        mContext = context;
        mList = list;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.top_popwindow_item, null);
            holder = new ViewHolder();

            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_image = (ImageView) convertView.findViewById(R.id.tv_image);
            if(ids != -1){
                holder.tv_image.setImageResource(ids);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PopWindowInfo info = mList.get(position);
        if (info != null) {
            String name = info.name;
            holder.tv_name.setText(name == null ? "" : name);
            if (info.isSelected) {
                holder.tv_name.setTextColor(Color.parseColor("#30C261"));
                holder.tv_image.setVisibility(View.VISIBLE);
            } else {
                holder.tv_image.setVisibility(View.GONE);
                holder.tv_name.setTextColor(Color.parseColor("#666666"));
            }
        }

        return convertView;
    }


    private class ViewHolder {
        private TextView tv_name;
        private ImageView tv_image;
    }
}