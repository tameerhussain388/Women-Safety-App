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
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            //When there was an error
                             Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(context, DashboardActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }
                });
    }
}
