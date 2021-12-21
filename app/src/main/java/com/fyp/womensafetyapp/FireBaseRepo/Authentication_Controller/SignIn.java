package com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.fyp.womensafetyapp.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn {
    static FirebaseAuth auth=FirebaseAuth.getInstance();
    public static void singInUser(String email, String password, Context context)
    {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (!task.isSuccessful()) {
                        //When there was an error
                         Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(context, DashboardActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
    }
}
