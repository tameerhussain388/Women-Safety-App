package com.fyp.womensafetyapp.Screens;
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
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller.SignOut;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseGuardians;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Services.ScreenOnOffBackgroundService;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.LocalDBHelper;
import com.fyp.womensafetyapp.utils.NetworkHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.DataInput;

public class DashboardActivity extends AppCompatActivity {
    public Button btnAlert;
    public Button btnCenters;
    public Button btnCallPolice;
    public Button btnGuardian;
    public LoadingDialogBar dialogBar;
    private static DashboardActivity instance;
    private final static int REQUEST_CODE = 123;
    private FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataFetcher();
        setContentView(R.layout.activity_dashboard);
        instance = this;
        Intent backgroundService = new Intent(getApplicationContext(), ScreenOnOffBackgroundService.class);
        startService(backgroundService);
        dialogBar = new LoadingDialogBar(this);
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

    public static DashboardActivity getInstance(){
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_btn:
                SignOut.signOutUser(this);
                break;
            case R.id.profile_btn:
                Intent intent = new Intent(DashboardActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
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
    public void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {

                locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        sendMessage(location.getLatitude(), location.getLongitude());
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

    private void sendMessage(double latitude, double longitude) {
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

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            dataSetter();
            Toast.makeText(this,"User :: "+new LocalDBRepo(this).fetchUser().name+"\n"+"email ::"+new LocalDBRepo(this).fetchUser().email,Toast.LENGTH_LONG).show();
        },5000);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataFetcher();
    }

    private void dataFetcher() {
        if(NetworkHelper.getInstance().haveNetworkConnection(this))
        {
            FirebaseUser.fetchUser(Firebase_Auth.getInstance().getUid());
            FirebaseGuardians.fetchGuardians(Firebase_Auth.getInstance().getUid());
        }else
        {
            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
        }
    }

    private void dataSetter()
    {
        LocalDBRepo localDBRepo=new LocalDBRepo(this);
        if(NetworkHelper.getInstance().haveNetworkConnection(this))
        {
            if(!LocalDBHelper.getInstance().hasUserData(this))
            {
                if(FirebaseUser.getUser()!=null)
                {
                    localDBRepo.storeUser(FirebaseUser.getUser());
                }else{
                    Toast.makeText(this,"An error occurred during fetching users from network",Toast.LENGTH_LONG).show();
                }

            } if(!LocalDBHelper.getInstance().hasGuardiansData(this))
            {
                if(FirebaseGuardians.getGuardians()!=null)
                {
                    Log.i("guardians if","Guardians if called");
                    localDBRepo.storeGuardians(FirebaseGuardians.getGuardians());
                }else
                {
                    Log.i("guardians else","Guardians else called");
                    Toast.makeText(this,"you haven't added your guardians yet please add",Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(this,"no internet connection",Toast.LENGTH_LONG).show();
        }

    }
}