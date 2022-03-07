package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StoreGuardians {

    private DocumentReference ref;
    LocalDBRepo localDBRepo;
    LoadingDialogBar dialogBar;
    public void storeGuardians(GuardiansModel guardian,String uid, Context context)
    {
        dialogBar=new LoadingDialogBar(context);
        dialogBar.showDialog("Loading");
        localDBRepo=new LocalDBRepo(context);
        Log.i("UID",uid);
        try {
            ref = FireStore.instance().collection("guardians").document(uid);
            ref.get().addOnSuccessListener(documentSnapshot -> {
                dialogBar.hideDialog();
                if (!documentSnapshot.exists()) {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("guardians", Arrays.asList(guardian.g1,guardian.g2));
                    reg_entry.put("gID", uid);
                    FireStore.instance().collection("guardians").document(uid).set(reg_entry)
                            .addOnSuccessListener(documentReference -> {
                                guardian.gID=uid;
                                localDBRepo.storeGuardians(guardian);
                                Toast.makeText(context, "Guardians successfully added", Toast.LENGTH_SHORT).show();
                                ((Activity) context).finish();
                            })
                            .addOnFailureListener(e -> {
                                dialogBar.hideDialog();
                                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
                                Log.d("Error", e.getMessage());
                            });
                }
            });
        }catch (Exception e)
        {
            Log.i("Exception e:: ",e.getMessage());
        }
    }

    public void updateGuardians(GuardiansModel guardian,String uid,Context context)
    {
        dialogBar=new LoadingDialogBar(context);
        dialogBar.showDialog("Loading");
        Map<String, Object> reg_entry = new HashMap<>();
        reg_entry.put("guardians", Arrays.asList(guardian.g1,guardian.g2));
        reg_entry.put("gID", uid);
        FirebaseFirestore.getInstance()
                .collection("guardians")
                .document(Firebase_Auth.getInstance().getUid())
                .set(reg_entry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new LocalDBRepo(context).updateGuardians(guardian);
                        dialogBar.hideDialog();
                        Toast.makeText(context,"Guardians updated successfully",Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogBar.hideDialog();
                        Toast.makeText(context,"Oops!! Something went wrong try again",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
