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
    static FirebaseAuth auth=FirebaseAuth.getInstance();
    static Context ctx;
    public static void singInUser(String email, String password, Context context)
    {
        ctx=context;
        AuthPreferences authPreferences=new AuthPreferences();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (!task.isSuccessful()) {
                        //When there was an error
                         Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
//                        Log.i("auth_token :: ",FirebaseAuth
//                                .getInstance()
//                                .getAccessToken(true)
//                                .addOnCompleteListener(task1 -> {
//                                    String jwt = task1.getResult().getToken();
//
//                                }).toString());
                        authPreferences.storeLoginFlag(true,context);
                        Intent intent = new Intent(context, DashboardActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
    }
}
