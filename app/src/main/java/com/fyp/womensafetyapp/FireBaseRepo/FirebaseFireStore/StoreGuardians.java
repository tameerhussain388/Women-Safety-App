package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StoreGuardians {

    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    DocumentReference ref;
    public void storeGuardians(String g1Contact, String g2Contact, String g3Contact, Context context)
    {
        ref=firebaseFirestore.collection("guardians").document();
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists())
                {
                    Toast.makeText(context, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("guardian_1",g1Contact);
                    reg_entry.put("guardian_2",g2Contact);
                    reg_entry.put("guardian_3",g2Contact);

                    firebaseFirestore.collection("guardians")
                            .add(reg_entry)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("Success","Data added");
                                    Toast.makeText(context, "Guardians successfully added", Toast.LENGTH_SHORT).show();
                                    ((Activity) context).finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error", e.getMessage());
                                }
                            });
                }
            }
        });
    }
}
