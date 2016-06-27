package com.xhao.pkg;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 应用程序信息类
 * Created by WongxHao on 2016/6/27 21:04
 */
public class AppInfo {
    private String label;
    private String packageName;
    private Drawable icon;
    private Intent intent;

    public AppInfo() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "label='" + label + '\'' +
                ", packageName='" + packageName + '\'' +
                ", icon=" + icon +
                ", intent=" + intent +
                '}';
    }
}
