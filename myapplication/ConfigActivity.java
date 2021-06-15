package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
     private static final String TAG = "ConfigActivity";
     EditText dollarEditor, euroEditor,wonEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent conf = getIntent();
        float dollar = conf.getFloatExtra("dollar_rate_key",0.0f);
        float euro = conf.getFloatExtra("euro_rate_key",0.0f);
        float won = conf.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG, "onCreate: dollar=" + dollar);
        Log.i(TAG, "onCreate: euro=" + euro);
        Log.i(TAG, "onCreate: won=" + won);

        dollarEditor = findViewById(R.id.dollar);
        euroEditor = findViewById(R.id.euro);
        wonEditor = findViewById(R.id.won);

        dollarEditor.setText(String.valueOf(dollar));
        euroEditor.setText(String.valueOf(euro));
        wonEditor.setText(String.valueOf(won));
    }
    public void save(View btn){
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuro = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }
}