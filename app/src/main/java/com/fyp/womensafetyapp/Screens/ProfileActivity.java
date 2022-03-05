package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.GuardiansModel;
import com.fyp.womensafetyapp.Models.UserModel;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.SetterFetcherHelper;

public class ProfileActivity extends AppCompatActivity {

    public TextView tvName;
    public TextView tvEmail;
    public TextView tvPhone;
    public TextView tvAge;
    public TextView tvGuardianOne;
    public TextView tvGuardianTwo;
    LoadingDialogBar dialogBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialogBar=new LoadingDialogBar(this);
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
        GuardiansModel guardian = db.fetchGuardians();
        if(!user.name.isEmpty())
        {
            tvName.setText(user.name);
            tvEmail.setText(user.email);
            tvPhone.setText(user.number);
            tvAge.setText(user.age);
            if(guardian != null){
                tvGuardianOne.setText(guardian.g1);
                tvGuardianTwo.setText(guardian.g2);
            }
        }else {
            dialogBar.showDialog("Loading...");
            SetterFetcherHelper.getInstance().dataFetcher(this);
            new Handler().postDelayed(() -> {
                SetterFetcherHelper.getInstance().dataSetter(this);
            },3000);
            new Handler().postDelayed(() -> {
                dialogBar.hideDialog();
                UserModel newUser = db.fetchUser();
                GuardiansModel newGuardians = db.fetchGuardians();
                tvName.setText(newUser.name);
                tvEmail.setText(newUser.email);
                tvPhone.setText(newUser.number);
                tvAge.setText(newUser.age);
                tvGuardianOne.setText(newGuardians.g1);
                tvGuardianTwo.setText(newGuardians.g2);
            },4000);
        }

    }
}