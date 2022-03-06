package com.fyp.womensafetyapp.Screens;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
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
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Services.ScreenOnOffBackgroundService;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.LocalDBHelper;
import com.fyp.womensafetyapp.utils.NetworkHelper;
import com.fyp.womensafetyapp.utils.SetterFetcherHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Dash;

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
        SetterFetcherHelper.getInstance().dataFetcher(this);
        setContentView(R.layout.activity_dashboard);
        instance = this;
        registerService();
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
        dialogBar.showDialog("Please wait");

        new Handler().postDelayed(() -> {
            SetterFetcherHelper.getInstance().dataSetter(this);
        },3000);
        new Handler().postDelayed(() -> {
            dialogBar.hideDialog();
        },4000);
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

    public void registerService(){
        if(!foregroundServiceRunning()){
            Intent backgroundService = new Intent(this, ScreenOnOffBackgroundService.class);
            ContextCompat.startForegroundService(this,backgroundService);
        }
    }

    public boolean foregroundServiceRunning(){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(ScreenOnOffBackgroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {

                LocalDBRepo repo = new LocalDBRepo(this);
                GuardiansModel guardian = repo.fetchGuardians();
                if(guardian == null){
                    Toast.makeText(getApplicationContext(),"Please add guardians",Toast.LENGTH_LONG).show();
                    return;
                }
                locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        sendMessage(guardian,location);
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

    private void sendMessage(GuardiansModel guardian, Location location) {
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            SmsManager sms = SmsManager.getDefault();
            String message = "Emergency!\nNeed Help\n";
            message += "Last Known Location:\n";
            message += "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude;
            String[] numbers = {guardian.g1,guardian.g2};
            for(String number: numbers){
                sms.sendTextMessage(number, null, message, null, null);
            }
            Toast.makeText(getApplicationContext(),"Alert Sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Alert Sending Failed", Toast.LENGTH_SHORT).show();
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
//    @Override
//    protected void onResume() {
//        super.onResume();
//        dialogBar.showDialog("Please wait...");
//        new Handler().postDelayed(this::dataSetter,3000);
//        new Handler().postDelayed(() -> {
//            dialogBar.hideDialog();
//        },4000);
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        dataFetcher();
        SetterFetcherHelper.getInstance().dataFetcher(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,ScreenOnOffBackgroundService.class));
    }

    //    private void dataFetcher() {
//        if(NetworkHelper.getInstance().haveNetworkConnection(this))
//        {
//            FirebaseUser.fetchUser(Firebase_Auth.getInstance().getUid());
//            FirebaseGuardians.fetchGuardians(Firebase_Auth.getInstance().getUid(),this);
//        }else
//        {
//            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void dataSetter()
//    {
//        LocalDBRepo localDBRepo=new LocalDBRepo(this);
//        if(NetworkHelper.getInstance().haveNetworkConnection(this))
//        {
//            if(!LocalDBHelper.getInstance().hasUserData(this))
//            {
//                if(FirebaseUser.getUser()!=null)
//                {
//                    localDBRepo.storeUser(FirebaseUser.getUser());
//                }else{
//                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
//                }
//
//            } if(!LocalDBHelper.getInstance().hasGuardiansData(this))
//            {
//                if(FirebaseGuardians.getGuardians()!=null)
//                {
//                    Log.i("guardians if","Guardians if called");
//                    localDBRepo.storeGuardians(FirebaseGuardians.getGuardians());
//                }else
//                {
//                    Log.i("guardians else","Guardians else called");
//                }
//            }
//        }else {
//            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
//        }
//
//    }
}