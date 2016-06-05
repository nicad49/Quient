package com.devshed42.quient;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by doug on 2016-06-04.
 */
public class QuientAudio {

    public static final void setQuientRingerMode(Context context, int ringerMode) {

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(ringerMode);
    }

    public static final int getQuientRingerMode(Context context) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audio.getRingerMode();
    }


    public static final String resolveQuientRingerMode(Context context, int ringerMode) {
        switch (ringerMode) {
            case 0:
                return context.getString(R.string.Silent);
            case 1:
                return context.getString(R.string.Vibrate);
            default:
                return context.getString(R.string.Normal);
        }
    }
}
