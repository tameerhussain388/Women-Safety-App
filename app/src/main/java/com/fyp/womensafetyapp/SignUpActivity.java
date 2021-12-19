package com.fyp.womensafetyapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Pattern;
import com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller.*;


public class SignUpActivity extends AppCompatActivity {

    public Button btnRegister;
    public EditText etName;
    public EditText etEmail;
    public EditText etPassword;
    public EditText etContact;
    public EditText etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUp firebaseRepo=new SignUp();

        btnRegister = findViewById(R.id.btnSignUp);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etContact = findViewById(R.id.etContact);
        etAge = findViewById(R.id.etAge);

        btnRegister.setOnClickListener(view -> {
            if (validateFields()) {
                firebaseRepo.signUpUser(etEmail.getText().toString(),etPassword.getText().toString(),SignUpActivity.this);
                firebaseRepo.storeUserData(etName.getText().toString(),etContact.getText().toString(),etAge.getText().toString(),this);
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