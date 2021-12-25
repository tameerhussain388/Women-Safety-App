package com.fyp.womensafetyapp;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fyp.womensafetyapp.databinding.ActivityEmergencyCentersBinding;
import com.google.android.gms.tasks.Task;

public class EmergencyCentersActivity extends FragmentActivity{

    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationProviderClient;
    private ActivityEmergencyCentersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyCentersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Task<Location> task = locationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> mapFragment.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        }));
    }
}