package com.example.roulette;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SocialRoulette extends Activity {

    // Elements
    Button buttonFire;
    Button buttonReload;
    Revolver revolver;
    ImageView chamber;
    LinearLayout mainScreen;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonFire = (Button) findViewById(R.id.buttonFire);
        buttonReload = (Button) findViewById(R.id.buttonReload);
        chamber = (ImageView) findViewById(R.id.imageView);
        mainScreen = (LinearLayout) findViewById(R.id.mainScreen);

        revolver = new Revolver();
        revolver.ctx = getApplicationContext();
        revolver.chamber = chamber;

        buttonFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revolver.fire();
            }
        });
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revolver.reload();
            }
        });

        mainScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        float initialY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
}
