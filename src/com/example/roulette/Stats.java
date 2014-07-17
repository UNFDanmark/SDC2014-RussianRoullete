package com.example.roulette;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import com.example.roulette.R;
import com.example.roulette.StatSaver;

public class Stats extends Activity {
    Typeface tf;
    TextView titleStats;
    TextView statstitle;
    TextView reloadsNumber;
    private StatSaver stats;
    public Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        // russian font
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/russian.ttf");


        statstitle = (TextView) findViewById(R.id.statstitle);
        statstitle.setTypeface(tf);

        titleStats = (TextView) findViewById(R.id.titleStats);
        titleStats.setTypeface(tf);

        reloadsNumber = (TextView) findViewById(R.id.reloadsNumber);
//        reloadsNumber.setText(stats.getInt(0));
    }
}