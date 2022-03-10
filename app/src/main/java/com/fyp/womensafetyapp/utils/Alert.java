package com.fyp.womensafetyapp.utils;

import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;

public class Alert {

    private final Context context;

    public Alert(Context context) {
        this.context = context;
    }

    public void send() {
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
                locationUtil.fetchLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location != null) {
                        LocalMessageSender sender = new LocalMessageSender(context);
                        sender.sendMessage(guardian, location);
                    } else {
                        Toast.makeText(context, "Error while fetching location", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                locationUtil.startLocationIntent();
            }
        } else {
            permission.requestAll();
        }
    }
}