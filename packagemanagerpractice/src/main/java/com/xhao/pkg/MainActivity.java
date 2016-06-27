package com.xhao.pkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button allApp;
    private Button systemApp;
    private Button thirdApp;
    private Button sdcardApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allApp = (Button) findViewById(R.id.btn_all_app);
        systemApp = (Button) findViewById(R.id.btn_system_app);
        thirdApp = (Button) findViewById(R.id.btn_third_app);
        sdcardApp = (Button) findViewById(R.id.btn_sdcard_app);
        allApp.setOnClickListener(this);
        systemApp.setOnClickListener(this);
        thirdApp.setOnClickListener(this);
        sdcardApp.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_app:
                AppListActivity.startActivity(this, AppListActivity.FILTER_ALL_APP);
                break;
            case R.id.btn_system_app:
                AppListActivity.startActivity(this, AppListActivity.FILTER_SYSTEM_APP);
                break;
            case R.id.btn_third_app:
                AppListActivity.startActivity(this, AppListActivity.FILTER_THIRD_APP);
                break;
            case R.id.btn_sdcard_app:
                AppListActivity.startActivity(this, AppListActivity.FILTER_SDCARD_APP);
                break;
        }
    }
}
