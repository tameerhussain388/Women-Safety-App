package com.fyp.womensafetyapp.Broadcasts;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;
import com.fyp.womensafetyapp.Utils.Alert;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action) || Intent.ACTION_SCREEN_OFF.equals(action)) {
            count++;
            if (count == 2) {
                count = 0;
                Alert alert = new Alert(context);
                alert.send(null);
            }
        }
    }
}