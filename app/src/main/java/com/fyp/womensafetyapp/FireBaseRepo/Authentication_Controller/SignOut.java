package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.LoginActivity;

public class SignOut {
    public static void signOutUser(Context context)
    {
       Firebase_Auth.getInstance().signOut();
        Toast.makeText(context, "Logout", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        (context).startActivity(intent);
        ((Activity)context).finish();
    }
}
