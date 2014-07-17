package com.example.roulette;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import com.example.roulette.Punishment;
import com.example.roulette.R;

public class Shot extends Activity {
    Typeface tf;
    TextView youdiedText;
    TextView messageText;
    Punishment punishment;

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
    }
}