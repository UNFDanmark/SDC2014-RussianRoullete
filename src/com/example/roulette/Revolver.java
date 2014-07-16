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
    private int maxRollSpeed = 800;
    private int minRollSpeed = 50;

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

    public void roll(long swipeSpeed, boolean swipeDirection) {
        // Lyd-effekt
        isRolled = true;
        rolledNumber = (int) (Math.random()*6+1);
        rollAnimation(swipeSpeed, swipeDirection);
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

    /*
    Animate gun chamber.
    swipeSpeed: int fr
     */
    private void rollAnimation(long swipeSpeed, boolean swipeDirection){
        RotateAnimation anim;

        if(swipeDirection) {
            anim = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);

        double swipePercentage = swipeSpeed / 16000.0;
        long calculatedDuration = (long)(maxRollSpeed - (maxRollSpeed - minRollSpeed) * swipePercentage);
        System.out.println(calculatedDuration);
        anim.setDuration(calculatedDuration);

        chamber.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            int numRepeats = 0;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // skift billede til pistol-løb
                //chamber.setImageResource(R.drawable.barrel);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setDuration((long)(animation.getDuration() + (numRepeats * 50) * animation.getDuration() / 1500.0));
                if(animation.getDuration() >= 1500){
                    chamber.clearAnimation();
                }

                numRepeats++;
            }
        });
    }

}

