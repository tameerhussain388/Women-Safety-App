package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;

public class ForgetPass {
    public void forgetPass(String email, Context context)
    {
        Firebase_Auth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context,"An email has been sent to your account",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(task ->
                Toast.makeText(context,task.getMessage(),Toast.LENGTH_SHORT).show());
    }
}
