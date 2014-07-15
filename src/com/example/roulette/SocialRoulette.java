package com.example.roulette;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SocialRoulette extends Activity {

    // Elements
    Button buttonFire;
    Button buttonReload;
    Revolver revolver;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonFire = (Button) findViewById(R.id.buttonFire);
        buttonReload = (Button) findViewById(R.id.buttonReload);
        revolver = new Revolver();
        buttonFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revolver.fire(getApplicationContext());
            }
        });
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revolver.reload(getApplicationContext());
            }
        });
    }
}
