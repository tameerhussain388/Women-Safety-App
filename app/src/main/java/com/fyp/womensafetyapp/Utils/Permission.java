package com.fyp.womensafetyapp.Utils;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;
import android.content.Context;
import com.karumi.dexter.Dexter;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Permission {

    private final Context context;

    public Permission(Context context) {
        this.context = context;
    }

    public void requestAll() {

        Dexter.withContext(context)
                .withPermissions(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                            Toast.makeText(context, "Please Grant Permissions to use the app", Toast.LENGTH_LONG).show();
                            ((Activity) context).finishAffinity();
                        } else {
                            LocationUtil location = new LocationUtil(context);
                            if (!location.isLocationEnabled())
                                location.startLocationIntent();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }

                }).check();
    }

    public boolean hasAllPermissions() {
        return hasLocationPermission() && hasCallPermission() && hasSmsPermission();
    }

    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasSmsPermission() {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasCallPermission() {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }
}
