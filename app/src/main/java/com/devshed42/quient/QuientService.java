package com.devshed42.quient;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class QuientService extends Service {

    private static QuientService instance = null;

    UserPresentBroadcastReceiver userPresentReceiver = new UserPresentBroadcastReceiver();


    public static boolean isQuientRunning() {
        return instance != null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(QuientService.this, "Service is started", Toast.LENGTH_SHORT).show();

        IntentFilter userPresentFilter = new IntentFilter();
        userPresentFilter.addAction(Intent.ACTION_USER_PRESENT);
        userPresentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(userPresentReceiver, userPresentFilter);

        PreferencesManager.saveOriginalState(this, QuientAudio.getQuientRingerMode(this));
        QuientAudio.setQuientRingerMode(this,PreferencesManager.getSavedAction(this));

        instance = this;
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {

        int originalRingerMode = PreferencesManager.getOriginalState(this);
        Log.d("QuientService", "Service stopping; returning phone to normal: " + originalRingerMode);
        QuientAudio.setQuientRingerMode(this, originalRingerMode);
        Toast.makeText(QuientService.this, "Service is stopped", Toast.LENGTH_SHORT).show();
        unregisterReceiver(userPresentReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
