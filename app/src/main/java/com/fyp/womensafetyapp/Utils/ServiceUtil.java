package com.fyp.womensafetyapp.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.fyp.womensafetyapp.Services.ScreenOnOffBackgroundService;

public class ServiceUtil {

    private final Context context;

    public ServiceUtil(Context context) {
        this.context = context;
    }

    public void registerService(){
        if(!foregroundServiceRunning()){
            Intent backgroundService = new Intent(context, ScreenOnOffBackgroundService.class);
            ContextCompat.startForegroundService(context,backgroundService);
        }
    }

    public boolean foregroundServiceRunning(){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(ScreenOnOffBackgroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    public void unregisterService(){
        Intent backgroundService = new Intent(context, ScreenOnOffBackgroundService.class);
        context.stopService(backgroundService);
    }
}
