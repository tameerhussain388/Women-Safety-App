package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.UserModel;
import com.google.firebase.firestore.DocumentReference;
import java.util.HashMap;
import java.util.Map;

public class StoreUser {
    LocalDBRepo localDBRepo;
    public void storeUserData(UserModel user, String uid, Context context)
    {
        localDBRepo=new LocalDBRepo(context);
        DocumentReference ref = FireStore.instance().collection("users").document(uid);
        ref.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
            {
                Toast.makeText(context, "Sorry,This user already exists", Toast.LENGTH_SHORT).show();
            } else {

                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("uID",uid);
                reg_entry.put("name",user.name);
                reg_entry.put("contact", user.number);
                reg_entry.put("age", user.age);
                reg_entry.put("email", user.email);

                FireStore.instance().collection("users").document(uid)
                        .set(reg_entry)
                        .addOnSuccessListener(documentReference -> {
                        })
                        .addOnFailureListener(e -> Log.d("Error", e.getMessage()));
            }
        });
    }
}
