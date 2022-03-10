package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;

import java.util.Map;
import android.util.Log;
import java.util.HashMap;
import android.widget.Toast;
import android.content.Context;
import com.fyp.womensafetyapp.Models.User;
import com.google.firebase.firestore.DocumentReference;

public class StoreUser {

    public void storeUserData(User user, String uid, Context context) {
        DocumentReference ref = FireStore.instance().collection("users").document(uid);
        ref.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
            {
                Toast.makeText(context, "Sorry,This user already exists", Toast.LENGTH_SHORT).show();
            } else {

                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("uID",uid);
                reg_entry.put("name",user.getName());
                reg_entry.put("contact", user.getNumber());
                reg_entry.put("age", user.getAge());
                reg_entry.put("email", user.getEmail());

                FireStore.instance().collection("users").document(uid)
                        .set(reg_entry)
                        .addOnFailureListener(e -> Log.d("Error", e.getMessage()));
            }
        });
    }
}
