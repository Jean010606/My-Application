package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyListActivity extends ListActivity implements Runnable {

    Handler handler;
    private static final String TAG = "MyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list);

        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    ArrayList<String> list = (ArrayList<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this,
                            android.R.layout.simple_list_item_1, list);
                    setListAdapter(adapter);
                    Toast.makeText(MyListActivity.this, "数据已更新", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Log.i(TAG, "run: ……");
        URL url = null;
        List<String>ret =  new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG,"run: title=" + doc.title());

            Element publicTime = doc.getElementsByClass( "time").first();
            Log.i(TAG, "run: time=" + publicTime.html());

            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if (tds.size()>0) {
                    Log.i(TAG,"run:td=" + tds.first().text());
                    Log.i(TAG, "run: rate=" + tds.get(5).text());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  Message msg = handler.obtainMessage(9,ret);
     //   msg.obj = "from message";
      //  handler.sendMessage(msg);
    }
}



