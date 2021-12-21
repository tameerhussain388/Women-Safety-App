package com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth;

import com.google.firebase.auth.FirebaseAuth;

public class Firebase_Auth {
    private static final FirebaseAuth instance=FirebaseAuth.getInstance();

    public static FirebaseAuth getInstance()
    {
        return instance;
    }
}
