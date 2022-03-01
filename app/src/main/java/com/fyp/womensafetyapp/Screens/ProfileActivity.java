package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.Models.UserModel;
import com.fyp.womensafetyapp.R;

public class ProfileActivity extends AppCompatActivity {

    public TextView tvName;
    public TextView tvEmail;
    public TextView tvPhone;
    public TextView tvAge;
    public TextView tvGuardianOne;
    public TextView tvGuardianTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvAge = findViewById(R.id.tvProfileAge);
        tvGuardianOne = findViewById(R.id.tvProfileGuardianOne);
        tvGuardianTwo = findViewById(R.id.tvProfileGuardianTwo);
        setUserProfile();
    }

    private void setUserProfile() {
        LocalDBRepo db = new LocalDBRepo(this);
        UserModel user = db.fetchUser();
        GuardiansModel guardians = db.fetchGuardians();
        tvName.setText(user.name);
        tvEmail.setText(user.email);
        tvPhone.setText(user.number);
        tvAge.setText(user.age);
        tvGuardianOne.setText(guardians.g1);
        tvGuardianTwo.setText(guardians.g2);
    }
}