package com.fyp.womensafetyapp.Screens;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.regex.Pattern;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller.*;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreUser;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.UserModel;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.NetworkHelper;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    public Button btnRegister;
    public EditText etName;
    public EditText etEmail;
    public EditText etPassword;
    public EditText etContact;
    public EditText etAge;
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
        btnRegister.setOnClickListener(view -> {
            if(NetworkHelper.getInstance().haveNetworkConnection(this))
            {
                if (validateFields()) {
                    dialogBar.showDialog("Loading");
                    UserModel user=new UserModel("",etName.getText().toString(),etContact.getText().toString(),etAge.getText().toString(),etEmail.getText().toString());
                    signUp(etEmail.getText().toString().trim(),etPassword.getText().toString(),user);

                }
            }else
            {
                Toast.makeText(this,"Please connect your device with internet",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUp(String email, String password, UserModel user)
    {
        StoreUser storeUser = new StoreUser();
        Firebase_Auth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    dialogBar.hideDialog();
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        storeUser.storeUserData(user,Firebase_Auth.getInstance().getUid(),SignUpActivity.this);
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateFields() {

        if (!validateName())
            return false;
        else if (!validateEmail())
            return false;
        else if (!validatePassword())
            return false;
        else if (!validateContact())
            return false;
        else return validateAge();
    }

    private boolean validateName() {
        String nameInput = etName.getText().toString().trim();
        Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]*$");
        if (nameInput.isEmpty()) {
            etName.setError("Name can't be empty");
            return false;
        } else if (!NAME_PATTERN.matcher(nameInput).matches()) {
            etName.setError("Name should contains only alphabets");
            return false;
        } else {
            etName.setError(null);
            return true;
        }
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
        Pattern PASSWORD_PATTERN = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        if (passwordInput.isEmpty()) {
            etPassword.setError("Password can't be empty");
            return false;
        } else if (passwordInput.length() < 8) {
            etPassword.setError("Password min length is 8 chars");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPassword.setError("Password should contain at least one number");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    private boolean validateContact() {
        String contactInput = etContact.getText().toString().trim();
        if (contactInput.isEmpty()) {
            etContact.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            etContact.setError("Contact should be 11 chars long");
            return false;
        } else {
            etContact.setError(null);
            return true;
        }
    }

    private boolean validateAge() {
        String ageInput = etAge.getText().toString().trim();
        if (ageInput.isEmpty()) {
            etAge.setError("Age can't be empty");
            return false;
        } else {
            etAge.setError(null);
            return true;
        }
    }
}