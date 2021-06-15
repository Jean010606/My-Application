package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class List3Activity extends ListActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "List3Activity";
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        //setContentView(R.layout.activity_list3);
        //ListView list3 = findViewById(R.id.mylist3);

        //data
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 50; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate: " + i);//标题文字
            map.put("ItemDetail", "Datail " + i);//详情描述
            listItems.add(map);

        }

        //adapter
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,//listItems 数据源
                R.layout.list_item,//listItem 的XML布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
        MyAdapter adapter = new MyAdapter(this, R.layout.list_item, listItems);
        //list3.setAdapter(listItemAdapter);
        //getListView().setAdapter(listItemAdapter);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 50; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemTitle", "Rate:" + i);
            map.put("ItemDetail", "detail:" + i);
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(this, listItems,
                R.layout.list_item, new String[]{"itemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: view=" + view);
        Log.i(TAG, "onItemClick: parent=" + parent);
        Log.i(TAG, "onItemClick: id=" + id);
        HashMap<String, String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr= " + detailStr);

        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);

        Intent rateCal = new Intent(this, List2Activity.class);
        rateCal.putExtra("title", titleStr);
        rateCal.putExtra("rate", Float.parseFloat(detailStr));
        startActivity(rateCal);

    }
}