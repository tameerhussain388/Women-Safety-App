package com.fyp.womensafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    public Button btnAlert;
    public Button btnCenters;
    public Button btnCallPolice;
    public Button btnGuardian;
    private final static int REQUEST_CODE = 123;
    private FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnAlert = findViewById(R.id.btnAlert);
        btnCenters = findViewById(R.id.btnCenters);
        btnGuardian = findViewById(R.id.btnGuardian);
        btnCallPolice = findViewById(R.id.btnCallPolice);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnAlert.setOnClickListener(view -> getLastLocation());

        btnCenters.setOnClickListener(view -> startCenterActivity());

        btnCallPolice.setOnClickListener(view -> notifyPolice());

        btnGuardian.setOnClickListener(view -> startGuardianActivity());
    }

    private void startCenterActivity() {
        Intent intent = new Intent(DashboardActivity.this,EmergencyCentersActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout_btn) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void startGuardianActivity() {
        Intent intent = new Intent(this, GuardianActivity.class);
        startActivity(intent);
    }

    private void notifyPolice() {
        if(ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            String dial = "tel:"+"03337244110";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
            startActivity(intent);
        }else{
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},0);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {

                locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        sendAlert(location.getLatitude(), location.getLongitude());
                        Toast.makeText(getApplicationContext(), " " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on your location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void sendAlert(double latitude, double longitude) {
        try {
            SmsManager sms = SmsManager.getDefault();
            String message = "Last Location:\n"+"https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
            String phoneNumber = "+923337244110";
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {

        String[] permissionArray = new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100).setNumUpdates(1);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Toast.makeText(getApplicationContext(), " " + mLastLocation.getLatitude(), Toast.LENGTH_LONG).show();
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                notifyPolice();
            }
        }
    }
}