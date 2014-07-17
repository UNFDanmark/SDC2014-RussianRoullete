package com.example.roulette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SocialRoulette extends Activity {
    // Elements
    Button buttonFire;
    Button buttonReload;
    Revolver revolver;
    ImageView chamber;
    LinearLayout parentScreen;
    TextView title;

    // Detectors
    GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    // Get dimensions
    Display display;
    Point screenDimensions;

    // Fonts
    Typeface tf;

    // Other
    Intent intent;
    StatSaver statSaver;


    private static final int SWIPE_MIN_DISTANCE = 200;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

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
        parentScreen = (LinearLayout) findViewById(R.id.parentScreen);
        title = (TextView) findViewById(R.id.title);

        revolver = new Revolver(this, chamber, parentScreen);

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/russian.ttf");
        title.setTypeface(tf);

        display = getWindowManager().getDefaultDisplay();
        screenDimensions = new Point();

        display.getSize(screenDimensions);

        statSaver = new StatSaver(this);

        // deactivate firebutton on default
        buttonFire.setEnabled(false);
        buttonFire.setAlpha(0.6f);

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

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        parentScreen.setOnTouchListener(gestureListener);

        intent = new Intent(getApplicationContext(), Stats.class);
        startActivity(intent);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        long speed;
        boolean direction;
        boolean vertical;
        int screenX = screenDimensions.x;
        int screenY = screenDimensions.y;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                direction = (e2.getY() < screenY / 2);
                vertical = false;
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                direction = (e2.getY() >= screenY / 2);
                vertical = false;
            // Down to up swipe
            } else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                direction = (e2.getX() >= screenX / 2);
                vertical = true;

            // Up to down swipe
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                direction = (e2.getX() < screenX / 2);
                vertical = true;

            }
            revolver.roll( vertical ? (long)Math.abs(velocityY) : (long)Math.abs(velocityX), direction);
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

}
