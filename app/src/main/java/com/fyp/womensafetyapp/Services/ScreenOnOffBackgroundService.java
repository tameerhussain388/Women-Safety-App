package com.fyp.womensafetyapp.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fyp.womensafetyapp.Broadcasts.ScreenOnOffReceiver;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Screens.DashboardActivity;

public class ScreenOnOffBackgroundService extends Service {
    private ScreenOnOffReceiver screenOnOffReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent mainIntent = new Intent(this, DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,mainIntent, 0);
        Notification notification = new NotificationCompat.Builder(this,"safetyId")
                .setContentTitle("Safety Service")
                .setContentText("Press power key to send alert")
                .setSmallIcon(R.drawable.android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        // Set broadcast receiver priority.
        intentFilter.setPriority(100);
        // Create a network change broadcast receiver.
        screenOnOffReceiver = new ScreenOnOffReceiver();
        // Register the broadcast receiver with the intent filter object.
        HandlerThread broadcastHandlerThread = new HandlerThread("SafetyThread");
        broadcastHandlerThread.start();
        Looper looper = broadcastHandlerThread.getLooper();
        Handler broadcastHandler = new Handler(looper);
        registerReceiver(screenOnOffReceiver,intentFilter,null,broadcastHandler);
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            NotificationChannel channel = new NotificationChannel(
                    "safetyId",
                    "Safety Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {

        stopForeground(true);
        stopSelf();
        // Unregister screenOnOffReceiver when destroy.
        if(screenOnOffReceiver!=null)
        {
            unregisterReceiver(screenOnOffReceiver);
        }
    }
}