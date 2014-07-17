package com.example.roulette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.roulette.Punishment;
import com.example.roulette.R;

public class Shot extends Activity {
    Typeface tf;
    TextView youdiedText;
    TextView messageText;
    Punishment punishment;
    Button buttonTryAgain;
    Button buttonStats;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot);
        youdiedText = (TextView) findViewById(R.id.youdied);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/russian.ttf");
        youdiedText.setTypeface(tf);

        punishment = new Punishment(getContentResolver(), getApplicationContext());
        punishment.getContactAndMessage();

        messageText = (TextView) findViewById(R.id.textViewMessage);
        messageText.setText("To: " + punishment.selectedName + "\n" + "Message:\n" + punishment.selectedMessage);
        punishment.sendSMS();

        buttonTryAgain = (Button) findViewById(R.id.buttonTryAgain);
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonStats = (Button) findViewById(R.id.buttonStats);
        intent = new Intent(getApplicationContext(), Stats.class);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}