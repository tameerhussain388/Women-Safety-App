package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.Screens.DashboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn {

    private static boolean isLoggedIn;

    public static boolean singInUser(String email, String password, Context context)
    {
        AuthPreferences authPreferences=new AuthPreferences();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (!task.isSuccessful()) {
                        isLoggedIn = false;
                    } else {
                        isLoggedIn = true;
                        authPreferences.storeLoginFlag(true,context);
                    }
                });
        return isLoggedIn;
    }
}