package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RateTask implements Runnable {


    private static final String TAG = "RateTask";
    private Handler handler;

    public void setHandler(Handler h) {
        this.handler = h;
    }

    @Override
    public void run() {
        Log.i(TAG,"run:...");
        URL url = null;
        List<Telephony.Mms.Rate> ret = new ArrayList<Telephony.Mms.Rate>();


        try {
            Thread.sleep(5000);

            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run: title=" + doc.title());

            //获取时间
            //body > section > div > div > article > p

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG, "run: time = " + publicTime.html());

            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 0) {
                    String str = tds.first().text();
                    Log.i(TAG, "run: td=" + str);

                    String val = tds.get(5).text();
                    Log.i(TAG, "run:rate=" + val);

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(11, ret);
        handler.sendMessage(msg);
    }
}