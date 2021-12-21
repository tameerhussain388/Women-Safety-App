package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StoreGuardians {

    private DocumentReference ref;
    public void storeGuardians(String g1Contact, String g2Contact, String g3Contact,String uid,Context context)
    {
        Log.i("UID",uid);
        try {
            ref = FireStore.instance().collection("guardians").document(uid);
            ref.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Toast.makeText(context, "Your guardians already exists", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("guardians", Arrays.asList(g1Contact,g2Contact,g3Contact));
                    FireStore.instance().collection("guardians").document(uid).set(reg_entry)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(context, "Guardians successfully added", Toast.LENGTH_SHORT).show();
                                ((Activity) context).finish();
                            })
                            .addOnFailureListener(e -> Log.d("Error", e.getMessage()));
                }
            });
        }catch (Exception e)
        {
            Log.i("Exception e:: ",e.getMessage());
        }
    }
}
