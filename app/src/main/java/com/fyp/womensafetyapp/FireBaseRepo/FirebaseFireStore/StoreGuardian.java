package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;

import java.util.Map;
import java.util.Arrays;
import android.util.Log;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Utils.LoadingDialogBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StoreGuardian {

    public void storeGuardians(Guardian guardian, String uid, Context context)
    {
        LoadingDialogBar dialogBar=new LoadingDialogBar(context);
        dialogBar.showDialog("Loading");
        LocalDBRepo localDBRepo=new LocalDBRepo(context);
        Log.i("UID",uid);
        try {
            DocumentReference ref = FireStore.instance().collection("guardians").document(uid);
            ref.get().addOnSuccessListener(documentSnapshot -> {
                dialogBar.hideDialog();
                if (!documentSnapshot.exists()) {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("guardians", Arrays.asList(guardian.getFirstContact(),guardian.getSecondContact()));
                    reg_entry.put("gID", uid);
                    FireStore.instance().collection("guardians").document(uid).set(reg_entry)
                            .addOnSuccessListener(documentReference -> {
                                guardian.setId(uid);
                                localDBRepo.storeGuardian(guardian);
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

    public void updateGuardians(Guardian guardian, String uid, Context context)
    {
        LoadingDialogBar dialogBar=new LoadingDialogBar(context);
        dialogBar.showDialog("Loading");
        Map<String, Object> reg_entry = new HashMap<>();
        reg_entry.put("guardians", Arrays.asList(guardian.getFirstContact(),guardian.getSecondContact()));
        reg_entry.put("gID", uid);
        FirebaseFirestore.getInstance()
                .collection("guardians")
                .document(Firebase_Auth.getInstance().getUid())
                .set(reg_entry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new LocalDBRepo(context).updateGuardian(guardian);
                        dialogBar.hideDialog();
                        Toast.makeText(context,"Guardians updated successfully",Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }
                })
                .addOnFailureListener(e -> {
                    dialogBar.hideDialog();
                    Toast.makeText(context,"Something went wrong try again",Toast.LENGTH_SHORT).show();
                });
    }
}