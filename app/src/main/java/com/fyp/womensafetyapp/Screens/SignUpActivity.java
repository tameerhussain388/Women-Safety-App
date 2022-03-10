package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.User;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.NetworkHelper;
import com.fyp.womensafetyapp.utils.Validator;

public class SignUpActivity extends AppCompatActivity {

    public EditText etName;
    public EditText etEmail;
    public EditText etPassword;
    public EditText etContact;
    public EditText etAge;
    public Button btnRegister;
    public LoadingDialogBar dialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnRegister = findViewById(R.id.btnSignUp);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etContact = findViewById(R.id.etContact);
        etAge = findViewById(R.id.etAge);
        dialogBar = new LoadingDialogBar(this);
        btnRegister.setOnClickListener(view -> registerUser());
    }

    public void registerUser(){
        if (NetworkHelper.getInstance().haveNetworkConnection(this)) {
            if (validateFields()) {
                dialogBar.showDialog("Loading");
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String contact = etContact.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                User user = new User(name, contact, age, email);
                signUp(user,password);
            }
        } else {
            Toast.makeText(this, "Please connect your device with internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp(User user,String password) {
        StoreUser storeUser = new StoreUser();
        Firebase_Auth.getInstance().createUserWithEmailAndPassword(user.getEmail(),password)
                .addOnCompleteListener(task -> {
                    dialogBar.hideDialog();
                    if (task.isSuccessful()) {
                        Objects.requireNonNull(Firebase_Auth.getInstance().getCurrentUser()).sendEmailVerification().addOnCompleteListener(result -> {
                            if (result.isSuccessful()) {
                                Toast.makeText(this,
                                        "A verification email has been sent to your account",
                                        Toast.LENGTH_LONG)
                                        .show();
                                storeUser.storeUserData(user, Firebase_Auth.getInstance().getUid(), this);
                                Intent intent = new Intent(this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Verification sending failed
                                Toast.makeText(
                                        this,
                                        "Failed to send verification email",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }).addOnFailureListener(result -> Toast.makeText(
                                this,
                                result.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show());
                    }
                }).addOnFailureListener(runnable ->
                    Toast.makeText(this, runnable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private boolean validateFields() {

        Validator validator = new Validator();
        if (!validator.validateName(etName))
            return false;
        else if (!validator.validateEmail(etEmail))
            return false;
        else if (!validator.validatePasswordWithPattern(etPassword))
            return false;
        else if (!validator.validateContact(etContact))
            return false;
        else return validator.validateAge(etAge);
    }
}