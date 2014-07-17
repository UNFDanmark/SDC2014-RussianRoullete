package com.example.roulette;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.roulette.R;
import com.example.roulette.StatSaver;

public class Stats extends Activity {
    Typeface tf;
    TextView titleStats;
    TextView statstitle;
    TextView reloadsNumber;
    TextView rollsNumber;
    TextView deathsNumber;
    TextView clicksNumber;

    Button backButton;

    private StatSaver statSaver;
    public Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        // russian font
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/russian.ttf");

        statSaver = new StatSaver(getApplicationContext());

        statstitle = (TextView) findViewById(R.id.statstitle);
        statstitle.setTypeface(tf);

        titleStats = (TextView) findViewById(R.id.titleStats);
        titleStats.setTypeface(tf);

        reloadsNumber = (TextView) findViewById(R.id.reloadsNumber);
        reloadsNumber.setText(String.valueOf(statSaver.getInt(0)));

        rollsNumber = (TextView) findViewById(R.id.rollsNumber);
        rollsNumber.setText(String.valueOf(statSaver.getInt(1)));

        deathsNumber = (TextView) findViewById(R.id.deathsNumber);
        deathsNumber.setText(String.valueOf(statSaver.getInt(2)));

        clicksNumber = (TextView) findViewById(R.id.clickNumber);
        clicksNumber.setText(String.valueOf(statSaver.getInt(3)));

        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}