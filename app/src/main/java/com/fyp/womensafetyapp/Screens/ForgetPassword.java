package com.fyp.womensafetyapp.Screens;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import com.fyp.womensafetyapp.FireBaseRepo.Authentication_Controller.ForgetPass;
import com.fyp.womensafetyapp.R;

public class ForgetPassword extends AppCompatActivity {
    Button sendPass;
    Button backButton;
    EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        sendPass=findViewById(R.id.sendPass);
        emailText=findViewById(R.id.etEmail);
        backButton=findViewById(R.id.back);
        sendPass.setOnClickListener(view -> {
            if(validateEmail())
            {
                new ForgetPass().forgetPass(emailText.getText().toString().trim(), this);
            }
        });
        backButton.setOnClickListener(view -> finish());
    }

    private boolean validateEmail() {
        String emailInput = emailText.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailText.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailText.setError("Invalid email address");
            return false;
        } else {
            emailText.setError(null);
            return true;
        }
    }
}