package com.fyp.womensafetyapp.Utils;

import android.util.Patterns;
import android.widget.EditText;
import java.util.regex.Pattern;

public class Validator {

    public boolean validateName(EditText editText) {
        String nameInput = editText.getText().toString().trim();
        Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]*$");
        if (nameInput.isEmpty()) {
            editText.setError("Name can't be empty");
            return false;
        } else if (!NAME_PATTERN.matcher(nameInput).matches()) {
            editText.setError("Name should contains only alphabets");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean validateEmail(EditText editText) {
        String emailInput = editText.getText().toString().trim();
        if (emailInput.isEmpty()) {
            editText.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editText.setError("Invalid email address");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean validatePassword(EditText editText) {
        String passwordInput = editText.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            editText.setError("Password can't be empty");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean validatePasswordWithPattern(EditText editText) {
        String passwordInput = editText.getText().toString().trim();
        Pattern PASSWORD_PATTERN = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        if (passwordInput.isEmpty()) {
            editText.setError("Password can't be empty");
            return false;
        } else if (passwordInput.length() < 8) {
            editText.setError("Password min length is 8 chars");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editText.setError("Password should contain at least one number");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    public boolean validateContact(EditText editText) {
        Pattern PHONE_PATTERN = Pattern.compile("^(03)([0-9]{9})$");
        String contactInput = editText.getText().toString().trim();
        if (contactInput.isEmpty()) {
            editText.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            editText.setError("Contact should be 11 chars long");
            return false;
        }
        else if (!PHONE_PATTERN.matcher(contactInput).matches()) {
            editText.setError("Please follow the format(03103455100)");
            return false;
        }
        else {
            editText.setError(null);
            return true;
        }
    }

    public boolean validateAge(EditText editText) {
        String ageInput = editText.getText().toString().trim();
        if (ageInput.isEmpty()) {
            editText.setError("Age can't be empty");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }
}
