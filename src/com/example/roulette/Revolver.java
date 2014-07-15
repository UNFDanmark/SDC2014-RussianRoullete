package com.example.roulette;


import android.content.Context;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Revolver {

    private boolean isLoaded = false;   // er rovolveren loaded?
    private int rolledNumber = 1;       // tilfældigt tal fra 1 til 6
    private int tries = 0;              // forsøg på skud
    private boolean isRolled = false;     // Er revolver cylinderen blevet rolled?


    public void reload(Context ctx) {
        // Lyd-effekt
        isLoaded = true;
    }

    public int roll() {
        // Lyd-effekt
        isRolled = true;
        return (int) (Math.random()*6+1);
    }

    public void fire(Context ctx) {
        if(!isRolled){
            Toast.makeText(ctx, "Roll the gun.", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isLoaded){
            Toast.makeText(ctx, "Reload the gun.", Toast.LENGTH_LONG).show();
            return;
        }


        if(rolledNumber == 6){
            // Skyd.
            // Lyd effekt
        }

    }

}
