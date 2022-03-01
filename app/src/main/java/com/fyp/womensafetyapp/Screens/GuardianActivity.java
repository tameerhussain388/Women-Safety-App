package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
    public StoreGuardians guardianStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);
        guardianStore = new StoreGuardians();
        etGuardianOne = findViewById(R.id.guardian_1);
        etGuardianTwo = findViewById(R.id.guardian_2);
        btnAddGuardian = findViewById(R.id.addGuardian);
        checkIfGuardianAlreadyExists();
        btnAddGuardian.setOnClickListener(view -> {
            if (validateFields()) {
                addGuardian();
            }
        });
    }

    private void checkIfGuardianAlreadyExists() {

        LocalDBRepo repo = new LocalDBRepo(GuardianActivity.this);
        GuardiansModel guardian = repo.fetchGuardians();
        if (guardian != null) {
            etGuardianOne.setText(guardian.g1);
            etGuardianTwo.setText(guardian.g2);
            etGuardianOne.setFocusable(false);
            etGuardianTwo.setFocusable(false);
            etGuardianOne.setTextColor(Color.WHITE);
            etGuardianTwo.setTextColor(Color.WHITE);
            etGuardianOne.setEnabled(false);
            etGuardianTwo.setEnabled(false);
            btnAddGuardian.setText("Guardians");
            btnAddGuardian.setEnabled(false);
        }
    }

    private boolean validateFields() {

        if (!validateGuardianOne())
            return false;
        else return validateGuardianTwo();
    }

    private void addGuardian() {
        String g1 = etGuardianOne.getText().toString();
        String g2 = etGuardianTwo.getText().toString();
        GuardiansModel guardian = new GuardiansModel("", g1, g2);
        guardianStore.storeGuardians(guardian, Firebase_Auth.getInstance().getUid(), this);
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