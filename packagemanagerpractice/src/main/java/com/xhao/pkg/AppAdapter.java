package com.xhao.pkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义适配器，提供给listView的自定义view
 * Created by WongxHao on 2016/6/27 21:06
 */
public class AppAdapter extends BaseAdapter {
    private static final String TAG = "AppAdapter";
    private List<AppInfo> mAppInfoList = null;

    LayoutInflater inflater = null;

    public AppAdapter(Context context, List<AppInfo> appInfoList) {
        //通过inflater获取view的第一种方法
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAppInfoList = appInfoList;
    }

    /**
     * 获取listView的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + mAppInfoList.size());
        return mAppInfoList.size();
    }

    /**
     * 获取listView的子项
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem: " + mAppInfoList.get(position).toString());
        return mAppInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: at " + position);
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null || convertView.getTag() == null){
            //通过inflater获取view的第二种方法
//            LayoutInflater.from(context).inflate(R.layout.app_list_item, null, false);
            view = inflater.inflate(R.layout.app_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AppInfo appInfo = (AppInfo) getItem(position);
        viewHolder.appLabel.setText(appInfo.getLabel());
        viewHolder.appPkgName.setText(appInfo.getPackageName());
        viewHolder.appIcon.setImageDrawable(appInfo.getIcon());

        return view;
    }

    /**
     * 使用ViewHolder缓存控件，加强listView的性能
     */
    class ViewHolder {
        ImageView appIcon;
        TextView appLabel;
        TextView appPkgName;

        public ViewHolder(View view) {
            this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
            this.appLabel = (TextView) view.findViewById(R.id.tv_app_label);
            this.appPkgName = (TextView) view.findViewById(R.id.tv_app_package_name);
        }
    }
}
