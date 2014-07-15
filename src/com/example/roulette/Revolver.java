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
        if (!isLoaded) {

            // skift billede til ladt
            chamber.setImageResource(R.drawable.chamber);

            Misc.message(ctx, "Reloaded");
            isLoaded = true;
            // Lyd-effekt
        } else {
            Misc.message(ctx, "Already loaded");
        }

    }

    public void roll() {
        // Lyd-effekt
        isRolled = true;
        rolledNumber = (int) (Math.random()*6+1);
        rollAnimation();
    }

    public void fire() {

        if(!isLoaded) {
            Misc.message(ctx, "Reload the gun");
            return;
        }
        if(!isRolled) {
            Misc.message(ctx, "Roll the gun");
            return;
        }



        if(isRolled && rolledNumber == 6) {
            // Skyd

            isLoaded = false;
            // Lyd effekt
        } else {
            chamber.setImageResource(R.drawable.chamber_empty);
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
            int numRepeats = 0;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // skift billede til pistol-løb
                chamber.setImageResource(R.drawable.barrel);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setDuration(animation.getDuration() + numRepeats * 10);
                if(numRepeats >= 11){
                    chamber.clearAnimation();
                }

                numRepeats++;
            }
        });
    }

}

