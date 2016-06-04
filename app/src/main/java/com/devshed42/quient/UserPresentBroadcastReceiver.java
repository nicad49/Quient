package com.devshed42.quient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    private String LOG_TAG = UserPresentBroadcastReceiver.class.getSimpleName();

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    final String PREF_ACTION = "ACTION";
    final String PREF_ORIGINAL_STATE = "ORIGINAL_STATE";

    final int SILENT = 0;
    final int VIBRATE = 1;
    public static final int PREF_SAVED_MODE_NO_VALUE = 2;


    public UserPresentBroadcastReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {

            int currentRingerMode = audio.getRingerMode();
            int ringerMode = preferences.getInt(PREF_ACTION,VIBRATE);
            editor.putInt(PREF_ORIGINAL_STATE, audio.getRingerMode()).apply();
            Toast.makeText(context, "From: " + currentRingerMode + " to: " + ringerMode, Toast.LENGTH_LONG).show();
            Log.d(LOG_TAG, "Current Ringer Mode: " + currentRingerMode);
            Log.d(LOG_TAG, "User is present.  Switch phone to: " + ringerMode);
            audio.setRingerMode(ringerMode);

        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            int originalRingerMode = preferences.getInt(PREF_ORIGINAL_STATE,PREF_SAVED_MODE_NO_VALUE);
            Log.d(LOG_TAG, "Screen off.  Switch phone to normal: " + originalRingerMode);
            audio.setRingerMode(originalRingerMode);
        }
    }
}
