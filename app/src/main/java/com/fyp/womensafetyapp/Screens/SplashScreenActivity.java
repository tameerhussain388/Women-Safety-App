package com.fyp.womensafetyapp.Screens;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    boolean loggedInFlag=false;
    AuthPreferences authPreferences=new AuthPreferences();
    private final static int SPLASH_SCREEN = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        loggedInFlag=authPreferences.getLoginFlag(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(loggedInFlag)
        {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashScreenActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            },SPLASH_SCREEN);

        }else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            },SPLASH_SCREEN);
        }
    }
}