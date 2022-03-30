package com.fyp.womensafetyapp.Senders;

import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import android.telephony.SmsManager;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Interfaces.LoaderCallback;
import com.fyp.womensafetyapp.Utils.Message;

public class LocalMessageSender {

    private final Context context;

    public LocalMessageSender(Context context) {
        this.context = context;
    }

    public void sendMessage(Guardian guardian, Location location, LoaderCallback callback) {
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            SmsManager sms = SmsManager.getDefault();
            String message = Message.getLastMessage(latitude,longitude);
            String[] numbers = {guardian.getFirstContact(),guardian.getSecondContact()};
            for(String number: numbers){
                sms.sendTextMessage(number, null, message, null, null);
            }
            if(callback != null) callback.finish();
            Toast.makeText(context,"Alert Sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Alert Sending Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}