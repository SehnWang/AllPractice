package com.xhao.pkg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "AppListActivity";

    public static final int FILTER_ALL_APP = 0;//所有应用程序
    public static final int FILTER_SYSTEM_APP = 1;//系统程序
    public static final int FILTER_THIRD_APP = 2;//第三方应用程序
    public static final int FILTER_SDCARD_APP = 3;//安装在SDCard的应用程序

    private ListView listView = null;

    private PackageManager pm;
    private int filter = FILTER_ALL_APP;
    private List<AppInfo> appInfoList;
    private AppAdapter adapter = null;

    public static void startActivity(Context context, int filter){
        Intent intent = new Intent(context, AppListActivity.class);
        intent.putExtra("filter", filter);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        listView = (ListView) findViewById(R.id.app_list);

        if(getIntent()!= null){
            filter = getIntent().getIntExtra("filter", FILTER_ALL_APP);
        }
        //查询所有应用程序的信息
        appInfoList = queryFilterAppInfo(filter);

        //构建适配器，并注册到ListView
        adapter = new AppAdapter(this, appInfoList);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
    }

    /**
     * 根据查询条件，查询特定的ApplicationInfo
     * @param filter
     * @return
     */
    private List<AppInfo> queryFilterAppInfo(int filter) {
        pm = this.getPackageManager();
        //查询所有的应用程序
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        //排序
        Collections.sort(applicationInfoList, new ApplicationInfo.DisplayNameComparator(pm));
        List<AppInfo> appInfoList = new ArrayList<>();

        //根据条件过滤
        switch (filter){
            case FILTER_ALL_APP://所有应用
                appInfoList.clear();
                for(ApplicationInfo applicationInfo : applicationInfoList){
                    appInfoList.add(getAppInfo(applicationInfo));
                }
                break;
            case FILTER_SYSTEM_APP://系统应用
                appInfoList.clear();
                for(ApplicationInfo applicationInfo : applicationInfoList){
                    if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                        appInfoList.add(getAppInfo(applicationInfo));
                    }
                }
                break;
            case FILTER_THIRD_APP://第三方应用
                appInfoList.clear();
                for(ApplicationInfo applicationInfo : applicationInfoList){
                    Log.i(TAG, "queryFilterAppInfo: flags -> " + applicationInfo.flags);
                    //非系统程序
                    if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0){
                        appInfoList.add(getAppInfo(applicationInfo));
                    }else if((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
                        //本来是系统程序，被用户手动更新以后，该系统程序也成为第三方应用程序
                        appInfoList.add(getAppInfo(applicationInfo));
                    }
                }
                break;
            case FILTER_SDCARD_APP://安装在SDCard的应用
                appInfoList.clear();
                for(ApplicationInfo applicationInfo : applicationInfoList){
                    if((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
                        appInfoList.add(getAppInfo(applicationInfo));
                    }
                }
                break;
            default:
                appInfoList.clear();
        }

        return appInfoList;
    }

    /**
     * 构造一个AppInfo对象，并赋值
     * @param applicationInfo
     * @return
     */
    private AppInfo getAppInfo(ApplicationInfo applicationInfo) {
        AppInfo appInfo = new AppInfo();
        appInfo.setLabel((String) applicationInfo.loadLabel(pm));
        appInfo.setPackageName(applicationInfo.packageName);
        appInfo.setIcon(applicationInfo.loadIcon(pm));
        return appInfo;
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
