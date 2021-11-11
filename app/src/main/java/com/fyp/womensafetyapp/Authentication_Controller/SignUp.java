package com.fyp.womensafetyapp.Authentication_Controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.fyp.womensafetyapp.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp {
    static FirebaseAuth auth=FirebaseAuth.getInstance();
    public static void signUpUser(String email, String password, Context context)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, "ERROR",Toast.LENGTH_LONG).show();
                }
                else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity) context).finish();
                }
            }
        });
    }
}
