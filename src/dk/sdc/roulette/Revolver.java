package dk.sdc.roulette;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.ViewConfiguration;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.media.MediaPlayer;

public class Revolver extends Activity {

    private boolean isLoaded = false;   // revolver not loaded yet
    private int rolledNumber = 1;       // random number from 1 to 6
    private boolean isRolled = false;   // did the chamber roll?
    private int maxRollSpeed = 800;     // max roll speed for chamber
    private int minRollSpeed = 50;      // min roll speed for chamber
    private boolean alwaysDie = false;
    private MediaPlayer mediaPlayer;
    private Flasher flasher;
    private Handler handler = new Handler();
    private StatSaver stats;

    public Context ctx;
    public ImageView chamber;           // chamber of the gun. Can be: chamber (loaded), empty or barrel
    public LinearLayout mainScreen;     // used to change background image/-color

    Revolver(Context ctx, ImageView chamber, LinearLayout mainScreen) {
        this.ctx = ctx;
        this.chamber = chamber;
        this.mainScreen = mainScreen;
        this.flasher = new Flasher(ctx);
        this.stats = new StatSaver(ctx);
    }

    public void reload() {

        // change to loaded chamber
        chamber.setImageResource(R.drawable.chamber);
        isLoaded = true;

        // deactivate reloadbutton
        ((SocialRoulette) ctx).buttonReload.setAlpha(0.6f);
        ((SocialRoulette) ctx).buttonReload.setEnabled(false);

        // count reloads
        stats.increment(0);


    }

    public void roll(long swipeSpeed, boolean swipeDirection) {     // death calculator
        if (isRolled)
            return;

        isRolled = true;
        rolledNumber = (int) (Math.random() * 6) + 1;
        rollAnimation(swipeSpeed, swipeDirection);
        stats.increment(1);
    }

    public void fire() {
        if (!isRolled) {
            Misc.message(ctx, "Please roll the gun");
            return;
        }

        // Shoot
        if (isRolled && rolledNumber == 6 || alwaysDie) {
            // soundeffect "bang"
            mediaPlayer = MediaPlayer.create(ctx, R.raw.bang);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

                ;
            });

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.start();
                }
            }, 300);

            flasher.flash(1);

            isLoaded = false;
            isRolled = false;

            // deactivate fire button
            ((SocialRoulette) ctx).buttonFire.setAlpha(0.6f);
            ((SocialRoulette) ctx).buttonFire.setEnabled(false);

            stats.increment(2);

            handler.postDelayed(new Runnable() {
                Intent intent = new Intent(ctx, Shot.class);

                @Override
                public void run() {
                    // activate reload button
                    ((SocialRoulette) ctx).buttonReload.setAlpha(1f);
                    ((SocialRoulette) ctx).buttonReload.setEnabled(true);

                    // show empty chamber img
                    chamber.setImageResource(R.drawable.chamber_empty);

                    // background flames
                    mainScreen.setBackgroundResource(R.drawable.realflames);

                    ((Activity) ctx).startActivity(intent);
                }
            }, 1000);
        } else {
            // soundeffect "click"
            mediaPlayer = MediaPlayer.create(ctx, R.raw.click);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                };
            });
            mediaPlayer.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stats.increment(3);

                    isLoaded = true;
                    isRolled = false;

                    // deactivate fire button
                    ((SocialRoulette) ctx).buttonFire.setAlpha(0.6f);
                    ((SocialRoulette) ctx).buttonFire.setEnabled(false);

                    // show loaded chamber img
                    chamber.setImageResource(R.drawable.chamber);

                    // background flames
                    mainScreen.setBackgroundResource(R.drawable.realflames);
                }
            }, 1300);
        }
    }


    private void showRevolver() {
        // show barrel img
        chamber.setImageResource(R.drawable.barrel);

        // black background
        mainScreen.setBackgroundColor(Color.parseColor("black"));

        // deactivate reloadbutton
        ((SocialRoulette) ctx).buttonReload.setAlpha(0.3f);
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

        double maxSwing = ViewConfiguration.get(ctx).getScaledMaximumFlingVelocity();
        double swipePercentage = swipeSpeed / maxSwing;
        long calculatedDuration = (long) (maxRollSpeed - (maxRollSpeed - minRollSpeed) * swipePercentage);

        rollAnim.setDuration(Math.abs(calculatedDuration));

        chamber.startAnimation(rollAnim);

        rollAnim.setAnimationListener(new Animation.AnimationListener() {
            int numRepeats = 0;

            @Override
            public void onAnimationStart(Animation animation) {

                // deactivate reloadbutton
                ((SocialRoulette) ctx).buttonReload.setEnabled(false);

                // soundeffect "rolling chamber"
                mediaPlayer = MediaPlayer.create(ctx, R.raw.chamber);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();

                    };
                });
                mediaPlayer.start();

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (!isLoaded) {
                    // activate reloadbutton
                    ((SocialRoulette) ctx).buttonReload.setAlpha(1f);
                    ((SocialRoulette) ctx).buttonReload.setEnabled(true);
                }

                try {
                        mediaPlayer.setVolume(0f, 0f);
                } catch (Exception e) {
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setDuration((long) (animation.getDuration() + (numRepeats * 50) * animation.getDuration() / 1500.0));
                if (animation.getDuration() >= 1000) {
                    if (isLoaded) {
                        hndl.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isRolled = true;

                                // stop animation
                                chamber.clearAnimation();

                                showRevolver();
                            }
                        }, (long) (500 + 500 * Math.random()));
                    } else {
                        isRolled = false;
                        chamber.clearAnimation();
                    }
                }

                numRepeats++;
            }
        });
    }


}

