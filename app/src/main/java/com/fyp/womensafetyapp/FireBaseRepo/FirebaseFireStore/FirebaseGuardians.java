package com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;

public class FirebaseGuardians {
    private static GuardiansModel guardians;
    public static GuardiansModel getGuardians()
    {
        return guardians;
    }
    public static void fetchGuardians(String uid)
    {
        FireStore.instance().collection("guardians").document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot=task.getResult();
            List<String> list= (List<String>) snapshot.get("guardians");
            if(list!=null)
            {
                guardians=new GuardiansModel(list.get(0),list.get(1),list.get(2));
            }
        });
    }
}
