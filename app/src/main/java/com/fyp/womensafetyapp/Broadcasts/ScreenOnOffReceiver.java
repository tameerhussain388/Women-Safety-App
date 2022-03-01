package com.fyp.womensafetyapp.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fyp.womensafetyapp.Screens.DashboardActivity;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_SCREEN_ON.equals(action))
        {
            count++;
            if(count == 2){
                DashboardActivity.getInstance().getLastLocation();
                count = 0;
            }
        }
    }
}