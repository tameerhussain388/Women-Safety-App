package com.fyp.womensafetyapp.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.fyp.womensafetyapp.FireBaseRepo.Controller.SignOut;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Services.ScreenOnOffBackgroundService;
import com.fyp.womensafetyapp.utils.Alert;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.ServiceUtil;
import com.fyp.womensafetyapp.utils.SetterFetcherHelper;

public class DashboardActivity extends AppCompatActivity {
    public Button btnAlert;
    public Button btnCenters;
    public Button btnCallPolice;
    public Button btnGuardian;
    public LoadingDialogBar dialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetterFetcherHelper.getInstance().dataFetcher(this);
        setContentView(R.layout.activity_dashboard);

        ServiceUtil serviceUtil = new ServiceUtil(this);
        serviceUtil.registerService();

        dialogBar = new LoadingDialogBar(this);
        btnAlert = findViewById(R.id.btnAlert);
        btnCenters = findViewById(R.id.btnCenters);
        btnGuardian = findViewById(R.id.btnGuardian);
        btnCallPolice = findViewById(R.id.btnCallPolice);

        btnAlert.setOnClickListener(view -> sendAlert());
        btnCenters.setOnClickListener(view -> startCenterActivity());
        btnCallPolice.setOnClickListener(view -> notifyPolice());
        btnGuardian.setOnClickListener(view -> startGuardianActivity());

        dialogBar.showDialog("Please wait");

        new Handler().postDelayed(() -> {
            SetterFetcherHelper.getInstance().dataSetter(this);
        }, 5000);
        new Handler().postDelayed(() -> {
            dialogBar.hideDialog();
        }, 6000);
    }

    private void sendAlert() {
        Alert alert = new Alert(this);
        alert.send();
    }

    private void startCenterActivity() {
        Intent intent = new Intent(DashboardActivity.this, EmergencyCentersActivity.class);
        startActivity(intent);
    }

    private void notifyPolice() {
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            String dial = "tel:" + "03353865985";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 0);
        }
    }

    private void startGuardianActivity() {
        Intent intent = new Intent(this, GuardianActivity.class);
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
            SignOut.signOutUser(this);
        } else if (item.getItemId() == R.id.profile_btn) {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SetterFetcherHelper.getInstance().dataFetcher(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ScreenOnOffBackgroundService.class));
    }
}