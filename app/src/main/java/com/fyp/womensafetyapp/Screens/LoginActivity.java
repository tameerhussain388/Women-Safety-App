package com.fyp.womensafetyapp.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.NetworkHelper;
import com.fyp.womensafetyapp.utils.Permission;
import com.fyp.womensafetyapp.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSignIn;
    private TextView tvRegister;
    private EditText etPassword;
    private TextView tvForgetPass;
    private LoadingDialogBar dialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister = findViewById(R.id.tvRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvForgetPass = findViewById(R.id.forget_pass);

        dialogBar = new LoadingDialogBar(this);

        Permission permission = new Permission(this);
        permission.requestAll();

        btnSignIn.setOnClickListener(view -> signInUser());
        tvRegister.setOnClickListener(view -> startSignUpActivity());
        tvForgetPass.setOnClickListener(view -> startForgetPasswordActivity());
    }

    private void signInUser() {
        if (NetworkHelper.getInstance().haveNetworkConnection(this)) {
            if (validateFields()) {
                dialogBar.showDialog("Loading");
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                signIn(email, password);
            }
        } else {
            Toast.makeText(this, "Please connect your device with internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void startForgetPasswordActivity() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private boolean validateFields() {

        Validator validator = new Validator();
        if (!validator.validateEmail(etEmail))
            return false;
        else return validator.validatePassword(etPassword);
    }

    private void signIn(String email, String password) {

        AuthPreferences authPreferences = new AuthPreferences();
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        dialogBar.hideDialog();
                        if (task.isSuccessful()) {
                            if (Objects.requireNonNull(Firebase_Auth.getInstance().getCurrentUser()).isEmailVerified()) {
                                authPreferences.storeLoginFlag(true, LoginActivity.this);
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Please verify your email first", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(task -> {
                        Toast.makeText(LoginActivity.this, task.getMessage(), Toast.LENGTH_SHORT).show();
                        etPassword.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}