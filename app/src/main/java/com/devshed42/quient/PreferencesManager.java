package com.devshed42.quient;

import android.content.Context;

/**
 * Created by doug on 2016-06-02.
 */
public class PreferencesManager {

    private static final String PREFS_NAME = "QuientPreferences";

    public static final String PREF_STATUS = "STATUS";
    public static final boolean PREF_STATUS_DEFAULT = false;

    public static final String PREF_ACTION = "ACTION";

    public static final String PREF_ORIGINAL_STATE = "ORIGINAL_STATE";
    public static final int PREF_ORIGINAL_STATE_DEFAULT = 2;

    public static final int VIBRATE = 1;


    public static final void saveState(Context context, boolean enabled) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(PREF_STATUS, enabled)
                .commit();
    }

    public static final boolean getSavedState(Context context) {
        return context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
                .getBoolean(PREF_STATUS,PREF_STATUS_DEFAULT);
    }

    public static final void saveAction(Context context, int action) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(PREF_ACTION, action)
                .commit();
    }

    public static final int getSavedAction(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(PREF_ACTION,VIBRATE);
    }

    public static final void saveOriginalState(Context context, int ringMode) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(PREF_ORIGINAL_STATE, ringMode)
                .commit();
    }

    public static final int getOriginalState(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(PREF_ORIGINAL_STATE, PREF_ORIGINAL_STATE_DEFAULT);
    }

}
