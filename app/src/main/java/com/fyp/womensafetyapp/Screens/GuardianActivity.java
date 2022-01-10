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

    public EditText guardian_1;
    public EditText guardian_2;
    public EditText guardian_3;
    public Button add_guardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);

        StoreGuardians guardians=new StoreGuardians();
        guardian_1=findViewById(R.id.guardian_1);
        guardian_2=findViewById(R.id.guardian_2);
        guardian_3=findViewById(R.id.guardian_3);
        add_guardian=findViewById(R.id.addGuardian);
        add_guardian.setOnClickListener(view -> {
            String g1=guardian_1.getText().toString();
            String g2=guardian_2.getText().toString();
            String g3=guardian_3.getText().toString();
            GuardiansModel guardian=new GuardiansModel("",g1,g2,g3);
            guardians.storeGuardians(guardian, Firebase_Auth.getInstance().getUid(),this);
        });
    }
}