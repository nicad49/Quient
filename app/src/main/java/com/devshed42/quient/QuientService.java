package com.devshed42.quient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class QuientService extends Service {
    public QuientService() {
    }

    UserPresentBroadcastReceiver userPresentReceiver = new UserPresentBroadcastReceiver();

    SharedPreferences preferences;
    final String PREF_ORIGINAL_STATE = "ORIGINAL_STATE";
    public static final int PREF_SAVED_MODE_NO_VALUE = 2;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Do something usefull
        Toast.makeText(QuientService.this, "Service is started", Toast.LENGTH_SHORT).show();

        IntentFilter userPresentFilter = new IntentFilter();
        userPresentFilter.addAction(Intent.ACTION_USER_PRESENT);
        userPresentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(userPresentReceiver, userPresentFilter);

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        // Clean-up tasks
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int originalRingerMode = preferences.getInt(PREF_ORIGINAL_STATE,PREF_SAVED_MODE_NO_VALUE);
        Log.d("QuientService", "Service stopping; returning phone to normal: " + originalRingerMode);
        audio.setRingerMode(originalRingerMode);
        Toast.makeText(QuientService.this, "Service is stopped", Toast.LENGTH_SHORT).show();
        unregisterReceiver(userPresentReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
