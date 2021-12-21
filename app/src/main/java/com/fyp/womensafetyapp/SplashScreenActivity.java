package com.fyp.womensafetyapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int SPLASH_SCREEN = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN);
    }

//    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//            if (firebaseUser != null) {
//                new Handler().postDelayed(() -> {
//                    Intent intent = new Intent(SplashScreenActivity.this,DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                },SPLASH_SCREEN);
//            }
//            else{
//                new Handler().postDelayed(() -> {
//                    Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                },SPLASH_SCREEN);
//            }
//        }
//    };
}