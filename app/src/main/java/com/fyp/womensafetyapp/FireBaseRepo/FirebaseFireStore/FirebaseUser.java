package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.util.Log;
import com.fyp.womensafetyapp.Models.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseUser {
    private static UserModel user;
    public static UserModel getUser()
    {
        return  user;
    }
    public static void fetchUser(String uid)
    {
       FireStore.instance()
                .collection("users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot=task.getResult();
                        user=new UserModel(snapshot.get("name").toString(),snapshot.get("contact").toString(),snapshot.get("age").toString());
                    } else {
                        Log.i("User ::","fail to get users");
                    }

                })
                .addOnFailureListener(e -> {
                    Log.i("failure :: ","Fail to load users");
                });
    }
}
