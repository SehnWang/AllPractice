package com.xhao.pkg.sample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xhao.pkg.AppAdapter;
import com.xhao.pkg.AppInfo;
import com.xhao.pkg.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "AppListActivity";
    private ListView listView = null;
    private List<AppInfo> appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        listView = (ListView) findViewById(R.id.app_list);
        appInfoList = new ArrayList<>();
        queryAppInfo();

        AppAdapter adapter = new AppAdapter(this, appInfoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    /**
     * 获取所有启动的Activity的信息
     */
    private void queryAppInfo() {
        PackageManager pm = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //通过查询，获取所有的ResolveInfo对象
        //PackageManager.MATCH_DEFAULT_ONLY：Category必须带有CATEGORY_DEFAULT的Activity才能匹配
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        Log.i(TAG, "queryAppInfo: " + resolveInfos.size());
        //调用系统排序，根据name排序
        //该排序很重要，否则只能显示系统应用，而不能列出第三方应用
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));

        if (appInfoList != null) {
            appInfoList.clear();

            for (ResolveInfo resolveInfo : resolveInfos) {
                String activityName = resolveInfo.activityInfo.name;//获取应用程序启动的activity的name
                String packageName = resolveInfo.activityInfo.packageName;//获取应用程序的包名
                String label = (String) resolveInfo.loadLabel(pm);//获取应用程序的label
                Drawable icon = resolveInfo.loadIcon(pm);//获取应用程序的图标
                Log.i(TAG, "queryAppInfo: this activity name -> " + activityName);

                //为应用程序的启动Activity 准备Intent
                Intent launcherIntent = new Intent();
                launcherIntent.setComponent(new ComponentName(packageName, activityName));

                //创建一个AppInfo对象 并赋值
                AppInfo appInfo = new AppInfo();
                appInfo.setLabel(label);
                appInfo.setPackageName(packageName);
                appInfo.setIntent(launcherIntent);
                appInfo.setIcon(icon);
                Log.i(TAG, "queryAppInfo: appInfo -> " + appInfo.toString());

                appInfoList.add(appInfo);//添加到列表中
            }
        }
    }

    /**
     * 点击listView的子项响应事件
     * 启动该项对应的应用程序
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = appInfoList.get(position).getIntent();
        startActivity(intent);
    }
}
