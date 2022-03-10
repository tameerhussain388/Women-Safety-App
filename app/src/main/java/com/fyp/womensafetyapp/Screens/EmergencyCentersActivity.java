package com.fyp.womensafetyapp.Screens;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.fyp.womensafetyapp.Models.Center;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LocationUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fyp.womensafetyapp.databinding.ActivityEmergencyCentersBinding;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmergencyCentersActivity extends FragmentActivity {

    private LocationUtil locationUtil;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmergencyCentersBinding binding = ActivityEmergencyCentersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationUtil = new LocationUtil(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (locationUtil.isLocationEnabled()) {
            getCurrentLocation();
        } else {
            locationUtil.startLocationIntent();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Task<Location> task = locationUtil.fetchLocation();
        task.addOnSuccessListener(location -> mapFragment.getMapAsync(googleMap -> {
            setCentersMarker(googleMap);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.setMyLocationEnabled(true);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
        }));
    }

    private void setCentersMarker(GoogleMap googleMap) {
        List<Center> centersList = getCenters();
        for (Center center : centersList) {
            LatLng latLng = new LatLng(center.getLat(), center.getLng());
            googleMap.addMarker(new MarkerOptions().position(latLng).title(center.getTitle()));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private List<Center> getCenters() {

        List<Center> centersArrayList = new ArrayList<>();
        InputStream stream = getResources().openRawResource(R.raw.centers);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String line;
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String title = tokens[0];
                Double lat = Double.parseDouble(tokens[1]);
                Double lng = Double.parseDouble(tokens[2]);
                Center center = new Center(title, lat, lng);
                centersArrayList.add(center);
            }
        } catch (IOException io) {
            Log.e("CentersActivity", "Connection Error");
        }
        return centersArrayList;
    }
}