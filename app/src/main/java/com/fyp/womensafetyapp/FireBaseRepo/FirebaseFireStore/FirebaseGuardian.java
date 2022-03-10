package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;

import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.Models.Guardian;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;

public class FirebaseGuardian {

    public static Guardian guardian;
    public static void fetchGuardians(String uid, Context context) {
        FireStore.instance().collection("guardians").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();
            List<String> gList = (List<String>) snapshot.get("guardians");
            String gID = (String) snapshot.get("gID");
            if (gList != null && gID != null) {
                guardian = new Guardian(gID, gList.get(0), gList.get(1));
            }
        }).addOnFailureListener(task -> {
            Toast.makeText(context, task.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}