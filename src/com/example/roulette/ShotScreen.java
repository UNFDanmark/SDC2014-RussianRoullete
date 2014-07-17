package com.example.roulette;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ShotScreen extends Activity {
    Typeface tf;
    TextView youdied;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skudt);
        youdied = (TextView) findViewById(R.id.youdied);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/russian.ttf");
        youdied.setTypeface(tf);
    }
}