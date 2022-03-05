package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;

public class FirebaseGuardians {
    public static GuardiansModel guardians;
    public static GuardiansModel getGuardians()
    {
        return guardians;
    }
    public static void fetchGuardians(String uid, Context context) {
        FireStore.instance().collection("guardians").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();
            List<String> gList = (List<String>) snapshot.get("guardians");
            String gID = (String) snapshot.get("gID");
            if (gList != null && gID != null) {
                guardians = new GuardiansModel(gID, gList.get(0), gList.get(1));
            }
        }).addOnFailureListener(task -> {
            Toast.makeText(context, task.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
