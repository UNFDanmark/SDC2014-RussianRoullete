package dk.sdc.roulette;

import android.content.Context;
import android.widget.Toast;

public class Misc {
    public static void message(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
