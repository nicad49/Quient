package com.devshed42.quient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class QuientService extends Service {
    public QuientService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
