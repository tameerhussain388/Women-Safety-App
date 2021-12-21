package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;
import java.util.HashMap;
import java.util.Map;

public class StoreUser {
    public void storeUserData(String name, String contact, String age, String uid, Context context)
    {
        DocumentReference ref = FireStore.instance().collection("users").document(uid);
        ref.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
            {
                Toast.makeText(context, "Sorry,This user already exists", Toast.LENGTH_SHORT).show();
            } else {

                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("uID",uid);
                reg_entry.put("name",name);
                reg_entry.put("contact", contact);
                reg_entry.put("age", age);

                FireStore.instance().collection("users").document(uid)
                        .set(reg_entry)
                        .addOnSuccessListener(documentReference -> {
                            Log.d("Success","Data added");
                            Toast.makeText(context, "User's data successfully added", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Log.d("Error", e.getMessage()));
            }
        });
    }
}
