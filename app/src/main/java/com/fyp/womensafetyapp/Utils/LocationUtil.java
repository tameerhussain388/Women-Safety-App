package com.fyp.womensafetyapp.Utils;

import android.os.Looper;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.location.LocationManager;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.FusedLocationProviderClient;

public class LocationUtil {

    private final Context context;
    private final FusedLocationProviderClient locationProviderClient;

    public LocationUtil(Context context) {
        this.context = context;
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void startLocationIntent() {
        Toast.makeText(context, "Please turn on your location", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    public Task<Location> fetchLocation() {

        return locationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    Location currentLocation = task.getResult();
                    if (currentLocation == null) {
                        requestNewLocationData();
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData() {

        LocationRequest mLocationRequest = LocationRequest.create()
                .setInterval(5)
                .setFastestInterval(10)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(10).setNumUpdates(1);
        locationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                locationResult.getLastLocation();
            }
        }, Looper.myLooper());
    }
}