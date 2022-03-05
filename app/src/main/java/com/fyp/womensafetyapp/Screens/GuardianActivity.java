package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreGuardians;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.R;

import java.util.regex.Pattern;

public class GuardianActivity extends AppCompatActivity {

    public String guardianId;
    public boolean isExist;
    public EditText etGuardianOne;
    public EditText etGuardianTwo;
    public Button btnSave;
    public StoreGuardians guardianStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);
        guardianStore = new StoreGuardians();
        etGuardianOne = findViewById(R.id.guardian_1);
        etGuardianTwo = findViewById(R.id.guardian_2);
        btnSave = findViewById(R.id.btnSave);
        checkIfGuardianExists();
        btnSave.setOnClickListener(view -> {
            if (validateFields()) {
                String g1 = etGuardianOne.getText().toString();
                String g2 = etGuardianTwo.getText().toString();
                if(isExist){
                    updateGuardian(g1,g2);
                }else{
                    addGuardian(g1,g2);
                }
            }
        });
    }

    private void checkIfGuardianExists() {
        LocalDBRepo repo = new LocalDBRepo(this);
        GuardiansModel guardian = repo.fetchGuardians();
        if(guardian != null){
            isExist = true;
            guardianId = guardian.gID;
            etGuardianOne.setText(guardian.g1);
            etGuardianTwo.setText(guardian.g2);
            Toast.makeText(getApplicationContext(),guardian.g1,Toast.LENGTH_LONG).show();
            btnSave.setText(R.string.update);
        }else{
            isExist = false;
            btnSave.setText(R.string.add);
        }
    }

    private boolean validateFields() {

        if (!validateGuardianOne())
            return false;
        else return validateGuardianTwo();
    }

    private void addGuardian(String g1,String g2) {
        GuardiansModel guardian = new GuardiansModel("", g1, g2);
        guardianStore.storeGuardians(guardian, Firebase_Auth.getInstance().getUid(), this);
    }

    private void updateGuardian(String g1,String g2) {
//        Update in local db as well as firestore
        GuardiansModel guardian = new GuardiansModel(guardianId, g1, g2);
        Toast.makeText(getApplicationContext(),guardianId+ " updated",Toast.LENGTH_LONG).show();
    }

    private boolean validateGuardianOne() {
        Pattern PHONE_PATTERN = Pattern.compile("^(03)([0-9]{9})$");
        String contactInput = etGuardianOne.getText().toString().trim();
        if (contactInput.isEmpty()) {
            etGuardianOne.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            etGuardianOne.setError("Contact should be 11 chars long");
            return false;
        }
        else if (!PHONE_PATTERN.matcher(contactInput).matches()) {
            etGuardianOne.setError("Please follow the given format");
            return false;
        }
        else {
            etGuardianOne.setError(null);
            return true;
        }
    }

    private boolean validateGuardianTwo() {
        Pattern PHONE_PATTERN = Pattern.compile("^(03)([0-9]{9})$");
        String contactInput = etGuardianTwo.getText().toString().trim();
        if (contactInput.isEmpty()) {
            etGuardianTwo.setError("Contact can't be empty");
            return false;
        } else if (!(contactInput.length() == 11)) {
            etGuardianTwo.setError("Contact should be 11 chars long");
            return false;
        }
        if (!PHONE_PATTERN.matcher(contactInput).matches()) {
            etGuardianTwo.setError("Please follow the given format");
            return false;
        }
        else {
            etGuardianTwo.setError(null);
            return true;
        }
    }
}