package com.example.roulette;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sdc on 7/16/14.
 */
public class ShotScreen extends Activity {
    Typeface tf;
    TextView youdied;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot);
        youdied = (TextView) findViewById(R.id.youdied);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/russian.ttf");
        youdied.setTypeface(tf);
    }
}