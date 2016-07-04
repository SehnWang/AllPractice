package com.xhao.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ItemAdapter itemAdapter;
    private List<Item> mItemlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_list);
        initDate();
        lv.setAdapter(itemAdapter);
    }

    private void initDate() {
        mItemlist = new ArrayList<>();

        Item item = new Item("这是一个标题","这是一段描述，用来显示的描述。","2016-07-04", "10086");
        mItemlist.add(item);
        item = new Item("这是一个标题","这是一段描述，用来显示的描述。","2016-07-04", "10086");
        mItemlist.add(item);
        item = new Item("这是一个标题","这是一段描述，用来显示的描述。","2016-07-04", "10086");
        mItemlist.add(item);
        item = new Item("这是一个标题","这是一段描述，用来显示的描述。","2016-07-04", "10086");
        mItemlist.add(item);

        itemAdapter = new ItemAdapter(this, mItemlist);
    }
}
