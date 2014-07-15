package com.example.roulette;


import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

public class Revolver {

    private boolean isLoaded = false;   // er rovolveren loaded?
    private int rolledNumber = 1;       // tilfældigt tal fra 1 til 6
    private int tries = 0;              // forsøg på skud
    private boolean isRolled = false;   // Er revolver cylinderen blevet rolled?
    public Context ctx;
    public ImageView chamber;

    public void reload() {
        if (isLoaded=false) {
            Misc.message(ctx, "Reloaded");
            // Lyd-effekt
            isLoaded = true;
        } else {
            Misc.message(ctx, "Already loaded");
        }

    }

    public void roll() {
        // Lyd-effekt
        isRolled = true;
        rollAnimation();
        rolledNumber = (int) (Math.random()*6+1);
    }

    public void fire() {
        if(!isRolled){
            Misc.message(ctx, "Roll the gun");
            return;
        }
        if(isLoaded){
            Misc.message(ctx, "Reload the gun");
            return;
        }


        if(rolledNumber == 6){
            // Skyd.
            isLoaded = false;
            // Lyd effekt
        }

    }

    private void rollAnimation(){
        RotateAnimation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);

        long randomDuration = 20 + (long)(Math.random() * 200);
        anim.setDuration(randomDuration);
        System.out.println(randomDuration);

        chamber.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                /*
                if(animation.getDuration() > 1500) {
                    chamber.clearAnimation();
                } else if(animation.getDuration() > 1000){
                    animation.setDuration(animation.getDuration() + 300);
                } else if(animation.getDuration() > 500){
                    animation.setDuration(animation.getDuration() + 200);
                } else {
                    animation.setDuration(animation.getDuration() + 50);
                }
                */
            }
        });
    }

}

