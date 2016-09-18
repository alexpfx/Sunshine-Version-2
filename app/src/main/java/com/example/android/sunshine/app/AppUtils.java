package com.example.android.sunshine.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexandre on 18/09/2016.
 */
public class AppUtils {

    public static SharedPreferences getSharedPreferences (Context context){
        return context.getSharedPreferences(context.getString(R.string.pref_key), Context.MODE_PRIVATE);
    }

}
