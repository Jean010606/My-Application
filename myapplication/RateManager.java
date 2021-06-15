package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RateManager {
    private static final String TAG = "db";
    DBHelper dbHelper;
    String TB_NAME;

    public RateManager(Context context){
        this.dbHelper = new DBHelper(context);
        this.TB_NAME = DBHelper.TB_NAME;
    }

    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("curname",item.getCurname());
        values.put("currate",item.getCurrate());

        long r = db.insert(TB_NAME,null,values);
        db.close();

        Log.i(TAG,"add: 写入结果r=" + r);
    }
}