package com.example.saversidekick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EarningsActivity extends AppCompatActivity {

    private EditText weeklyWageEditText;
    private EditText otherTypeIncomeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        weeklyWageEditText = findViewById(R.id.editTextWeeklyWages);
        otherTypeIncomeEditText =findViewById(R.id.editTextOtherTypeIncome);

        Button submitButton = findViewById(R.id.buttonSubmitEarnings);
        submitButton.setOnClickListener(view -> {
            String weeklyEarningsString = weeklyWageEditText.getText().toString();
            String otherTypeIncomeString = otherTypeIncomeEditText.getText().toString();
            double weeklyEarnings = Double.parseDouble(weeklyEarningsString);
            double otherTypeIncome = Double.parseDouble(otherTypeIncomeString);
            double totalEarnings = weeklyEarnings + otherTypeIncome;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EarningsActivity.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("totalEarnings", (float) totalEarnings);
            editor.apply();
            Intent intent = new Intent(EarningsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });
    }
}
