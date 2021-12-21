package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.FireStore;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreGuardians;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.LoginActivity;
import com.fyp.womensafetyapp.Models.UserModel;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class SignUp {
    StoreUser storeUser=new StoreUser();
    public void signUpUser(String email, String password, UserModel user, Context context)
    {
        Firebase_Auth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context,
                                "Registration successful!",
                                Toast.LENGTH_LONG)
                                .show();
                        // hide the progress bar
                        // progressBar.setVisibility(View.GONE);
                        storeUser.storeUserData(user.name,user.number,user.age,Firebase_Auth.getInstance().getUid(),context);
                        // if the user created intent to login activity
                        Intent intent = new Intent(context, LoginActivity.class);
                        (context).startActivity(intent);
                        ((Activity)context).finish();
                    }
                    else {
                        // Registration failed
                        Toast.makeText(
                               context,
                                task.getException().getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                        // hide the progress bar
                        // progressBar.setVisibility(View.GONE);
                    }
                });
    }

}