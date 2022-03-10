package com.fyp.womensafetyapp.Screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.content.Intent;
import com.fyp.womensafetyapp.R;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;
    private final static int SPLASH_SCREEN = 3000;
    private final AuthPreferences authPreferences = new AuthPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        isLoggedIn = authPreferences.getLoginFlag(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            Intent intent;
            if (isLoggedIn) {
                intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}