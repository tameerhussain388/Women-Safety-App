package com.fyp.womensafetyapp.Screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.Utils.Validator;
import com.fyp.womensafetyapp.Models.Guardian;
import androidx.appcompat.app.AppCompatActivity;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.FireBaseRepo.Firebase_Auth.Firebase_Auth;
import com.fyp.womensafetyapp.FireBaseRepo.FirebaseFireStore.StoreGuardian;

public class GuardianActivity extends AppCompatActivity {

    private Button btnSave;
    private boolean isExist;
    private String guardianId;
    private EditText etFirstContact;
    private EditText etSecondContact;
    private StoreGuardian guardianStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);

        guardianStore = new StoreGuardian();
        etFirstContact = findViewById(R.id.etFirstContact);
        etSecondContact = findViewById(R.id.etSecondContact);
        btnSave = findViewById(R.id.btnSave);

        checkIfGuardianExists();
        btnSave.setOnClickListener(view -> saveGuardian());
    }

    public void saveGuardian(){
        if (validateFields()) {
            String firstContact = etFirstContact.getText().toString();
            String secondContact = etSecondContact.getText().toString();
            if(isExist){
                updateGuardian(firstContact,secondContact);
            }else{
                addGuardian(firstContact,secondContact);
            }
        }
    }

    private void checkIfGuardianExists() {
        LocalDBRepo repo = new LocalDBRepo(this);
        Guardian guardian = repo.fetchGuardian();
        if(guardian != null){
            isExist = true;
            guardianId = guardian.getId();
            etFirstContact.setText(guardian.getFirstContact());
            etSecondContact.setText(guardian.getSecondContact());
            btnSave.setText(R.string.update);
        }else{
            isExist = false;
            btnSave.setText(R.string.add);
        }
    }

    private void addGuardian(String firstContact,String secondContact) {
//      Add guardians in local db as well as in firestore
        Guardian guardian = new Guardian(firstContact, secondContact);
        guardianStore.storeGuardians(guardian, Firebase_Auth.getInstance().getUid(), this);
    }

    private void updateGuardian(String firstContact,String secondContact) {
//      Updated guardians in local db as well as in firestore.
        Guardian guardian = new Guardian(guardianId, firstContact, secondContact);
        guardianStore.updateGuardians(guardian, Firebase_Auth.getInstance().getUid(), this);
    }

    private boolean validateFields() {

        Validator validator = new Validator();
        if (!validator.validateContact(etFirstContact))
            return false;
        else return validator.validateContact(etSecondContact);
    }
}