package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Screens.LoginActivity;
import com.fyp.womensafetyapp.Models.UserModel;

public class SignUp {

    public void signUpUser(String email, String password, UserModel user, Context context)
    {
        StoreUser storeUser = new StoreUser();
        Firebase_Auth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context,
                                "Registration successful!",
                                Toast.LENGTH_LONG)
                                .show();
                        // hide the progress bar
                        // progressBar.setVisibility(View.GONE);
                        storeUser.storeUserData(user,Firebase_Auth.getInstance().getUid(),context);
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
                    }
                });
    }

}