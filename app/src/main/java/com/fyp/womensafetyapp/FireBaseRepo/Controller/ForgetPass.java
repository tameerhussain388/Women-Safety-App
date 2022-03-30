package com.fyp.womensafetyapp.FireBaseRepo.Controller;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Utils.LoadingDialogBar;

public class ForgetPass {
    LoadingDialogBar loadingDialogBar;
    public void forgetPass(String email, Context context)
    {
        loadingDialogBar=new LoadingDialogBar(context);
        loadingDialogBar.showDialog("Please Wait");
        Firebase_Auth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context,"An email has been sent to your account",Toast.LENGTH_LONG).show();
                        loadingDialogBar.hideDialog();
                        ((Activity) context).finish();
                    }
                }).addOnFailureListener(task -> {
            Toast.makeText(context, task.getMessage(), Toast.LENGTH_SHORT).show();
            loadingDialogBar.hideDialog();
        });
    }
}
