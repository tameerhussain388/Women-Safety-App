package com.fyp.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    public Button add_guardian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        add_guardian = findViewById(R.id.btnThree);

        add_guardian.setOnClickListener(view -> {
            Toast.makeText(this, "Add guardian", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AddGuardians.class);
            startActivity(intent);
        });
    }
}