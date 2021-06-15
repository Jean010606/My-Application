package com.example.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class RateListActivity extends ListActivity implements Runnable {

    private static final String TAG = "RateActivity";
    EditText input;
    TextView result;
    float dollarRate = 0.15f;
    float euroRate = 0.12f;
    float wonRate = 172.0f;
    Handler handler;
    public void setHandler(Handler h){this.handler = h;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        input = findViewById(R.id.rmb);
        result = findViewById(R.id.result);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: get str=" + str);
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };


    }

    public void click(View btn) {
        Log.d(TAG, "click: ");

        float r = 0.0f;
        switch (btn.getId()) {
            case R.id.btn_dollar:
                r = dollarRate;
            case R.id.btn_euro:
                r = euroRate;
            case R.id.btn_won:
                r = wonRate;

        }
        String str = input.getText().toString();
        Log.i(TAG, "click: str=" + str);
        if (str == null || str.length() == 0) {
            Toast.makeText(this, "Please input RMB", Toast.LENGTH_SHORT).show();
        } else {

            result.setText("1234.444");
        }
    }

    public void openConfig(View btn) {
        Log.i(TAG, "openConfig:");
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openConfig: dollarRate=" + dollarRate);
        Log.i(TAG, "openConfig: euroRate=" + euroRate);
        Log.i(TAG, "openConfig: wonRate=" + wonRate);
        startActivityForResult(config, 5);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.1f);
            wonRate = bundle.getFloat("key_won", 0.1f);
            SharedPreferences sp =
                    getSharedPreferences("myrate", Activity.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            editor.apply();
            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void run() {

        Log.i(TAG, "run:run()...");
        for (int i = 1; i < 3; i++) {
            Log.i(TAG, "run: i=" + i);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            // url = new URL("http://www.usd-cny.com/bankofchina.htm");
            // HttpURLConnection http = (HttpURLConnection) url.openConnection();
            // InputStream in = http.getInputStream();
            // String html = inputStream2String(in);
            //Log.i(TAG,"run:html=" + html);
            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: title=" + doc.title());

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG, "run: time=" + publicTime.html());

            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 0) {
                    Log.i(TAG, "run:td=" + tds.first().text());
                    Log.i(TAG, "run: rate=" + tds.get(5).text());
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(9);
        msg.obj = "from message";
        handler.sendMessage(msg);


    }




    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }


}
