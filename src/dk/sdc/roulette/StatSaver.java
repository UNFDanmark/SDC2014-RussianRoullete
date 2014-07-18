package dk.sdc.roulette;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/*
Saving stats
*/
public class StatSaver{
    private SharedPreferences stats;
    private SharedPreferences.Editor editor;
    private String[] types = {
            "reloads",
            "rolls",
            "deaths",
            "clicks"
    };

    public StatSaver(Context ctx){
        stats = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = stats.edit();
    }

    public void increment(int typeIndex){
        String type = types[typeIndex];
        int oldValue = getInt(typeIndex);
        editor.putInt(type, oldValue + 1);
        editor.commit();
    }

    public int getInt(int type){
        return stats.getInt(types[type], 0);
    }




}
