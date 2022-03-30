package com.fyp.womensafetyapp.Utils;

import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Senders.LocalMessageSender;
import com.fyp.womensafetyapp.Senders.TwilioMessageSender;
import com.fyp.womensafetyapp.Interfaces.LoaderCallback;

public class Alert {

    private final Context context;

    public Alert(Context context) {
        this.context = context;
    }

    public void send(LoaderCallback callback) {
        LocalDBRepo repo = new LocalDBRepo(context);
        Guardian guardian = repo.fetchGuardian();
        if (guardian == null) {
            Toast.makeText(context, "Please add guardians", Toast.LENGTH_LONG).show();
            return;
        }
        Permission permission = new Permission(context);
        LocationUtil locationUtil = new LocationUtil(context);
        if (permission.hasLocationPermission() && permission.hasSmsPermission()) {
            // check if location is enabled
            if (locationUtil.isLocationEnabled()) {
                if(callback != null) callback.start();
                locationUtil.fetchLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location != null) {
                       send(guardian,location,callback);
                    }else{
                        if(callback != null) callback.finish();
                        Toast.makeText(context.getApplicationContext(),"Error while sending alert",Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                locationUtil.startLocationIntent();
            }
        } else {
            permission.requestAll();
        }
    }

    private void send(Guardian guardian, Location location,LoaderCallback callback) {

        if(NetworkHelper.getInstance().haveNetworkConnection(context)){
            TwilioMessageSender twilioMessageSender = new TwilioMessageSender(context);
            twilioMessageSender.sendMessage(guardian,location,callback);
        }else{
            LocalMessageSender localMessageSender = new LocalMessageSender(context);
            localMessageSender.sendMessage(guardian,location,callback);
        }
    }
}