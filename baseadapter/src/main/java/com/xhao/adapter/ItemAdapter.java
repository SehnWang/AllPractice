package com.xhao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by doco on 2016/7/4.
 */
public class ItemAdapter extends BaseAdapter{
    private Context mContext;
    private List<Item> mItemList;


    public ItemAdapter(Context context, List<Item> items){
        this.mContext = context;
        this.mItemList = items;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;

        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.lv_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.tv_time);
            viewHolder.tvPhone = (TextView) view.findViewById(R.id.tv_phone);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = mItemList.get(position);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvDesc.setText(item.getDesc());
        viewHolder.tvTime.setText(item.getTime());
        viewHolder.tvPhone.setText(item.getPhone());

        return view;
    }

    class ViewHolder{
        TextView tvTitle;
        TextView tvDesc;
        TextView tvTime;
        TextView tvPhone;

    }
}
