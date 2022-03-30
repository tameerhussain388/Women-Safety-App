package com.fyp.womensafetyapp.FireBaseRepo.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FirebaseGuardian;
import com.fyp.womensafetyapp.Screens.LoginActivity;
import com.fyp.womensafetyapp.Utils.ServiceUtil;

public class SignOut {

    public static void signOutUser(Context context) {
        LocalDBRepo localDBRepo = new LocalDBRepo(context);
        new AuthPreferences().deleteLogin(context);
        localDBRepo.deleteUser(localDBRepo.fetchUser().getId());
        if (FirebaseGuardian.guardian != null) {
            Log.i("Guardian if ", "if called");
            localDBRepo.deleteGuardian(FirebaseGuardian.guardian.getId());
            FirebaseGuardian.guardian = null;
        }
        ServiceUtil serviceUtil = new ServiceUtil(context);
        serviceUtil.unregisterService();
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        (context).startActivity(intent);
        ((Activity) context).finish();
    }
}