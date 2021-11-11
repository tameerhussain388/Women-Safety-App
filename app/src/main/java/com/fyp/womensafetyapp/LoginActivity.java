package com.fyp.womensafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.womensafetyapp.Authentication_Controller.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public TextView tvRegister;
    public Button btnSignIn;
    public EditText etEmail;
    public EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        tvRegister = findViewById(R.id.tvRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(view -> {
            if (validateEmail() && validatePassword()) {
//                Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                startActivity(intent);
                if (TextUtils.isEmpty(etEmail.toString())) {
                    Toast.makeText(getApplicationContext(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etPassword.toString())) {
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //authenticate user
                SignIn.singInUser(etEmail.getText().toString(),etPassword.getText().toString(),LoginActivity.this);
            }
        });
        tvRegister.setOnClickListener(view -> {
            Toast.makeText(this, "Sign Up", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateEmail() {
        String emailInput = etEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            etEmail.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Invalid email address");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = etPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            etPassword.setError("Password can't be empty");
            return false;
        }
        else {
            etPassword.setError(null);
            return true;
        }
    }
}