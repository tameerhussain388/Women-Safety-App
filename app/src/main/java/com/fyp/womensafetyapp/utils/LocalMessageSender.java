package com.fyp.womensafetyapp.utils;

import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import android.telephony.SmsManager;
import com.fyp.womensafetyapp.Models.Guardian;

public class LocalMessageSender {

    private final Context context;

    public LocalMessageSender(Context context) {
        this.context = context;
    }

    public void sendMessage(Guardian guardian, Location location) {
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            SmsManager sms = SmsManager.getDefault();
            String message = "Emergency!\nNeed Help\n";
            message += "Last Known Location:\n";
            message += "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
            String[] numbers = {guardian.getFirstContact(),guardian.getSecondContact()};
            for(String number: numbers){
                sms.sendTextMessage(number, null, message, null, null);
            }
            Toast.makeText(context,"Alert Sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Alert Sending Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}