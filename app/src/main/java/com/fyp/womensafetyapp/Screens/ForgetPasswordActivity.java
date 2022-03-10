package com.fyp.womensafetyapp.Screens;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.fyp.womensafetyapp.FireBaseRepo.Controller.ForgetPass;
import com.fyp.womensafetyapp.R;
import com.fyp.womensafetyapp.utils.Validator;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        btnSend = findViewById(R.id.btnSend);
        etEmail = findViewById(R.id.etForgetEmail);

        btnSend.setOnClickListener(view -> {
            Validator validator = new Validator();
            if(validator.validateEmail(etEmail))
            {
                new ForgetPass().forgetPass(etEmail.getText().toString().trim(), this);
            }
        });
    }
}