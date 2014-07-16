package com.example.roulette;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Revolver {

    private boolean isLoaded = false;   // revolver not loaded yet
    private int rolledNumber = 1;       // random number from 1 to 6
    private int tries = 0;              // count tries to shoot
    private boolean isRolled = false;   // did the chamber roll?
    private int maxRollSpeed = 800;     // max roll speed for chamber
    private int minRollSpeed = 50;      // min roll speed for chamber

    public Context ctx;                 // holds toast syntax
    public ImageView chamber;           // chamber of the gun. Can be: empty, loaded or chamber
    public LinearLayout mainScreen;     // used to change background image/-color

    public void reload() {
        if (!isLoaded) {

            // skift billede til ladt
            chamber.setImageResource(R.drawable.chamber);

            Misc.message(ctx, "Loaded the gun");
            isLoaded = true;
            // Lyd-effekt
        } else {
            Misc.message(ctx, "Already loaded");
        }

    }

    public void roll(long swipeSpeed, boolean swipeDirection) {
        if (isRolled) {
            return;
        }

        // Lyd-effekt
        isRolled = true;
        rolledNumber = (int) (Math.random() * 6 + 1);
        rollAnimation(swipeSpeed, swipeDirection);
    }

    public void fire() {

        if (!isLoaded) {
            Misc.message(ctx, "Please load the gun");
            return;
        }

        if (!isRolled) {
            Misc.message(ctx, "Please roll the gun");
            return;
        }


        if (isRolled && rolledNumber == 6) {
            // Skyd

            isLoaded = false;
            // soundeffect
        }
    }

    private void showRevolver() {

        // show chamber img
        chamber.setImageResource(R.drawable.barrel);

        // black background
        mainScreen.setBackgroundColor(Color.parseColor("black"));

        // stop animation
        chamber.clearAnimation();

        // deactivate reloadbutton

        // activate firebutton
    }


    /*
    Animate gun chamber.
    swipeSpeed: int fr
     */
    private void rollAnimation(long swipeSpeed, boolean swipeDirection) {
        final Handler hndl = new Handler();

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        RotateAnimation rollAnim;

        if (swipeDirection) {
            rollAnim = new RotateAnimation(360f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rollAnim = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rollAnim.setInterpolator(new LinearInterpolator());
        rollAnim.setRepeatCount(Animation.INFINITE);

        double swipePercentage = swipeSpeed / 16000.0;
        long calculatedDuration = (long) (maxRollSpeed - (maxRollSpeed - minRollSpeed) * swipePercentage);
        rollAnim.setDuration(calculatedDuration);

        chamber.startAnimation(rollAnim);

        rollAnim.setAnimationListener(new Animation.AnimationListener() {
            int numRepeats = 0;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setDuration((long) (animation.getDuration() + (numRepeats * 50) * animation.getDuration() / 1500.0));
                if (animation.getDuration() >= 1500) {
                    hndl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showRevolver();
                        }
                    }, (long) (500 + 500 * Math.random()));
                }

                numRepeats++;
            }
        });
    }
}

