package com.example.saversidekick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EarningsActivity extends AppCompatActivity {

    private EditText weeklyEarningsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        weeklyEarningsEditText = findViewById(R.id.editTextWeeklyEarnings);

        Button submitButton = findViewById(R.id.buttonSubmitEarnings);
        submitButton.setOnClickListener(view -> {
            String weeklyEarningsString = weeklyEarningsEditText.getText().toString();
            double weeklyEarnings = Double.parseDouble(weeklyEarningsString);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EarningsActivity.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("weeklyEarnings", (float) weeklyEarnings);
            editor.apply();
            Intent intent = new Intent(EarningsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });
    }
}