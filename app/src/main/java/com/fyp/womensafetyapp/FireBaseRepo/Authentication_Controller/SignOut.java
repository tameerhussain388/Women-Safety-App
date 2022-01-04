package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Screens.LoginActivity;

public class SignOut {
    public static void signOutUser(Context context)
    {
       new AuthPreferences().deleteToken(context);
        Toast.makeText(context, "Logged out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        (context).startActivity(intent);
        ((Activity)context).finish();
    }
}
