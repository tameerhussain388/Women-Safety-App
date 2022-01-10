package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseGuardians;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Screens.LoginActivity;

public class SignOut {
    public static void signOutUser(Context context)
    {
        LocalDBRepo localDBRepo=new LocalDBRepo(context);
       new AuthPreferences().deleteToken(context);
       localDBRepo.deleteUser(FirebaseUser.getUser().uID);
       if(FirebaseGuardians.getGuardians()!=null)
       {
           Log.i("Guardian if ","if called");
           localDBRepo.deleteGuardian(FirebaseGuardians.getGuardians().gID);
       }
        Toast.makeText(context, "Logged out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        (context).startActivity(intent);
        ((Activity)context).finish();
    }
}
