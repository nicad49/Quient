package com.devshed42.quient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    private String LOG_TAG = UserPresentBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {


            int currentRingerMode = QuientAudio.getQuientRingerMode(context);


            int ringerMode = PreferencesManager.getSavedAction(context);
            PreferencesManager.saveOriginalState(context, currentRingerMode);

            Toast.makeText(context, "From: " +
                    QuientAudio.resolveQuientRingerMode(context, currentRingerMode) + " to: " +
                    QuientAudio.resolveQuientRingerMode(context, ringerMode), Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Current Ringer Mode: " + currentRingerMode);
            Log.d(LOG_TAG, "User is present.  Switch phone to: " + ringerMode);

            QuientAudio.setQuientRingerMode(context, ringerMode);

        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            int originalRingerMode = PreferencesManager.getOriginalState(context);
            Log.d(LOG_TAG, "Screen off.  Switch phone to normal: " + originalRingerMode);
            QuientAudio.setQuientRingerMode(context, originalRingerMode);
        }
    }
}
