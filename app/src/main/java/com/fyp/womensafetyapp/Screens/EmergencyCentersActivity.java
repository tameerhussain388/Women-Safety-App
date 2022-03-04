package com.fyp.womensafetyapp.Screens;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.fyp.womensafetyapp.Models.CenterModel;
import com.fyp.womensafetyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmergencyCentersActivity extends FragmentActivity {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmergencyCentersBinding binding = ActivityEmergencyCentersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (isLocationEnabled()) {
            getCurrentLocation();
        } else {
            Toast.makeText(this, "Please turn on your location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Task<Location> task = locationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> mapFragment.getMapAsync(googleMap -> {
            setCentersMarker(googleMap);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.setMyLocationEnabled(true);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        }));
    }

    private void setCentersMarker(GoogleMap googleMap) {
        mMap = googleMap;
        List<CenterModel> centersList = getCenters();
        for (CenterModel center : centersList) {
            LatLng latLng = new LatLng(center.getLat(),center.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title(center.getTitle()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private List<CenterModel> getCenters(){

        List<CenterModel> centersArrayList = new ArrayList<>();
        InputStream stream = getResources().openRawResource(R.raw.centers);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String line = "";
        try{
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");
                String title = tokens[0];
                Double lat = Double.parseDouble(tokens[1]);
                Double lng = Double.parseDouble(tokens[2]);
                CenterModel center = new CenterModel(title,lat,lng);
                centersArrayList.add(center);
            }
        }catch (IOException io){
            Log.e("CentersActivity","Connection Error");
        }
        return centersArrayList;
    }
}