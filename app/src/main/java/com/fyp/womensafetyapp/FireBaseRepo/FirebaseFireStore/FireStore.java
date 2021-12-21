package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FireStore {
    private static final FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private static final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();
    public static FirebaseFirestore instance()
    {
        firebaseFirestore.setFirestoreSettings(settings);
        return firebaseFirestore;
    }
}
