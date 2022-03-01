package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Data.SharedPreferences.AuthPreferences;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreGuardians;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.R;

public class GuardianActivity extends AppCompatActivity {

    public EditText etGuardianOne;
    public EditText etGuardianTwo;
    public Button btnAddGuardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);

        StoreGuardians guardians = new StoreGuardians();
        etGuardianOne = findViewById(R.id.guardian_1);
        etGuardianTwo = findViewById(R.id.guardian_2);
        btnAddGuardian = findViewById(R.id.addGuardian);
        btnAddGuardian.setOnClickListener(view -> {
            if (validateFields()) {
                String g1 = etGuardianOne.getText().toString();
                String g2 = etGuardianTwo.getText().toString();
                GuardiansModel guardian = new GuardiansModel("", g1, g2);
                guardians.storeGuardians(guardian, Firebase_Auth.getInstance().getUid(), this);
            }
        });
    }

    private boolean validateFields() {

        if (!validateGuardianOne())
            return false;
        else return validateGuardianTwo();
    }

    private boolean validateGuardianOne() {
        String contactInput = etGuardianOne.getText().toString().trim();
        if (contactInput.isEmpty()) {
            etGuardianOne.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            etGuardianOne.setError("Contact should be 11 chars long");
            return false;
        } else {
            etGuardianOne.setError(null);
            return true;
        }
    }

    private boolean validateGuardianTwo() {
        String contactInput = etGuardianTwo.getText().toString().trim();
        if (contactInput.isEmpty()) {
            etGuardianTwo.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            etGuardianTwo.setError("Contact should be 11 chars long");
            return false;
        } else {
            etGuardianTwo.setError(null);
            return true;
        }
    }
}