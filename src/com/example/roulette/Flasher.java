package com.example.roulette;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Flasher {
    private RelativeLayout flash;
    private RelativeLayout view;
    private Toast toast;


    public Flasher(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        flash = new RelativeLayout(context);
        flash.setLayoutParams(params);
        flash.setBackgroundColor(0xffffffff);
        flash.setVisibility(View.VISIBLE);

        view = new RelativeLayout(context);
        view.setLayoutParams(params);
        view.addView(flash);

        toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
    }


    public final void flash(int count) {
        toast.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 200);
    }
}
