package com.fyp.womensafetyapp.Screens;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.fyp.womensafetyapp.Data.LocalDBRepo.LocalDBRepo;
import com.fyp.womensafetyapp.Models.Guardian;
import com.fyp.womensafetyapp.Models.User;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.LoadingDialogBar;
import com.fyp.womensafetyapp.utils.LocalDBHelper;
import com.fyp.womensafetyapp.utils.SetterFetcherHelper;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvAge;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvGuardianOne;
    private TextView tvGuardianTwo;
    private LoadingDialogBar dialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dialogBar=new LoadingDialogBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvAge = findViewById(R.id.tvProfileAge);
        tvName = findViewById(R.id.tvProfileName);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvGuardianOne = findViewById(R.id.tvProfileGuardianOne);
        tvGuardianTwo = findViewById(R.id.tvProfileGuardianTwo);

        showProfile();
    }

    private void showProfile() {
        if(LocalDBHelper.getInstance().hasUserData(this)) {
            setProfile();
        }else {
            dialogBar.showDialog("Loading...");
            SetterFetcherHelper.getInstance().dataFetcher(this);
            new Handler().postDelayed(() -> SetterFetcherHelper.getInstance().dataSetter(this),4000);
            new Handler().postDelayed(() -> {
                setProfile();
                dialogBar.hideDialog();
            },5000);
        }
    }

    public void setProfile(){

        LocalDBRepo db = new LocalDBRepo(this);
        User user = db.fetchUser();
        if(user != null){
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getNumber());
            tvAge.setText(user.getAge());
            Guardian guardian = db.fetchGuardian();
            if(guardian != null){
                tvGuardianOne.setText(guardian.getFirstContact());
                tvGuardianTwo.setText(guardian.getSecondContact());
            }
        }
    }
}