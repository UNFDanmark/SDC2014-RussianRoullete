package com.example.roulette;


import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.media.MediaPlayer;

import java.util.concurrent.TimeUnit;



public class Revolver {

    private boolean isLoaded = false;   // revolver not loaded yet
    private int rolledNumber = 1;       // random number from 1 to 6
    private int tries = 0;              // count tries to shoot
    private boolean isRolled = false;   // did the chamber roll?
    private int maxRollSpeed = 800;     // max roll speed for chamber
    private int minRollSpeed = 50;      // min roll speed for chamber
    private MediaPlayer mediaPlayer;

    public Context ctx;
    public ImageView chamber;           // chamber of the gun. Can be: empty, loaded or chamber
    public LinearLayout mainScreen;     // used to change background image/-color

    public void reload() {              // reload function
        if (!isLoaded) {

            // change to loaded chamber
            chamber.setImageResource(R.drawable.chamber);

            isLoaded = true;
        } else {
            Misc.message(ctx, "Already loaded");
        }

    }

    public void roll(long swipeSpeed, boolean swipeDirection) {     // death calculator
        if (isRolled) {
            return;
        }

        // soundeffect "rolling chamber"
        mediaPlayer = MediaPlayer.create(ctx, R.raw.chamber);
        mediaPlayer.start();



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

        // Shoot
        if (isRolled && rolledNumber == 6) {


            // flash img?

            // soundeffect "bang"
            mediaPlayer = MediaPlayer.create(ctx,R.raw.bang);
            mediaPlayer.start();

            isLoaded = false;
            isRolled = false;

            // deactivate fire button
            ((SocialRoulette) ctx).buttonFire.setAlpha(0.6f);
            ((SocialRoulette) ctx).buttonFire.setEnabled(false);

            // sleep until sound is played
            try {
                Thread.sleep(1000);
            } catch (Exception e){

            }

            // activate reload button
            ((SocialRoulette) ctx).buttonReload.setAlpha(1f);
            ((SocialRoulette) ctx).buttonReload.setEnabled(true);

            // show empty chamber img
            //System.out.println("Sunik ");
            chamber.setImageResource(R.drawable.chamber_empty);

            // background flames
            mainScreen.setBackgroundResource(R.drawable.realflames);

        } else {
            // soundeffect "click"
            MediaPlayer mediaPlayer = MediaPlayer.create(ctx,R.raw.click);
            mediaPlayer.start();

            isLoaded = true;
            isRolled = false;

            // deactivate fire button
            ((SocialRoulette) ctx).buttonFire.setAlpha(0.6f);
            ((SocialRoulette) ctx).buttonFire.setEnabled(false);

            // sleep until sound is played
            try {
                Thread.sleep(1000);
            } catch (Exception e){

            }

            // show loaded chamber img
            chamber.setImageResource(R.drawable.chamber);

            // background flames
            mainScreen.setBackgroundResource(R.drawable.realflames);

        }

    }



    private void showRevolver() {

        // show barrel img
        chamber.setImageResource(R.drawable.barrel);

        // black background
        mainScreen.setBackgroundColor(Color.parseColor("black"));

        // deactivate reloadbutton
        ((SocialRoulette) ctx).buttonReload.setAlpha(0.6f);
        ((SocialRoulette) ctx).buttonReload.setEnabled(false);

        // activate firebutton
        ((SocialRoulette) ctx).buttonFire.setAlpha(1f);
        ((SocialRoulette) ctx).buttonFire.setEnabled(true);
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
                mediaPlayer.stop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setDuration((long) (animation.getDuration() + (numRepeats * 50) * animation.getDuration() / 1500.0));
                if (animation.getDuration() >= 1500) {
                    hndl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isLoaded) {
                                showRevolver();
                            } else {
                                isRolled = false;
                            }
                            // stop animation
                            chamber.clearAnimation();
                        }
                    }, (long) (500 + 500 * Math.random()));
                }

                numRepeats++;
            }
        });
    }


}

