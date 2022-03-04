package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Models.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseUser {
    private static UserModel user;
    public static UserModel getUser()
    {
        return  user;
    }
    public static void fetchUser(String uid, Context context)
    {
       FireStore.instance()
                .collection("users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot=task.getResult();
                        user=new UserModel(snapshot.get("uID").toString(),snapshot.get("name").toString(),snapshot.get("contact").toString(),snapshot.get("age").toString(),snapshot.get("email").toString());
                    } else {
                        Toast.makeText(context,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                });
    }
}
