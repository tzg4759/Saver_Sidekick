package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize the UI elements
        TextView textViewNeeds = findViewById(R.id.textViewNeeds);
        TextView textViewEverythingElse = findViewById(R.id.textViewWants);
        TextView textViewSavings = findViewById(R.id.textViewSavings);
        TextView textViewEarnings = findViewById(R.id.textViewEarnings);
        TextView textViewWeeklyWage = findViewById(R.id.textViewWeeklyWage);
        TextView textViewOtherIncome = findViewById(R.id.textViewNewIncome);

        ProgressBar progressBarNecessities = findViewById(R.id.progressBarNecessities);
        ProgressBar progressBarWants = findViewById(R.id.progressBarWants);
        ProgressBar progressBarSavings = findViewById(R.id.progressBarSavings);

        Button goalsButton = findViewById(R.id.goalsButton);
        goalsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        // Retrieve the weekly earnings from SharedPreferences
        double weeklyWages = PreferenceManager.getDefaultSharedPreferences(this).getFloat("weeklyEarnings", 0);
        // retrieve the new income amount from SharedPreferences
        double newIncomeAmount = PreferenceManager.getDefaultSharedPreferences(this).getFloat("newIncomeAmount",0);

        double weeklyTotalEarnings = weeklyWages + newIncomeAmount;
        String newIncomeType = PreferenceManager.getDefaultSharedPreferences(this).getString("incomeType","Other Income");

        // Calculate the amounts for each category
        double necessities = weeklyTotalEarnings * 0.5;
        double wants = weeklyTotalEarnings * 0.3;
        double savings = weeklyTotalEarnings * 0.2;

        // Update the UI with the calculated amounts
        textViewNeeds.setText(String.format(Locale.US, "Necessities: $%.2f", necessities));
        textViewEverythingElse.setText(String.format(Locale.US, "Wants: $%.2f", wants));
        textViewSavings.setText(String.format(Locale.US, "Savings: $%.2f", savings));
        textViewWeeklyWage.setText(String.format(Locale.US,"Your Weekly Wage: $%.2f", weeklyWages));
        textViewOtherIncome.setText(String.format(Locale.US,"Your "+newIncomeType+": $%.2f", newIncomeAmount));
        textViewEarnings.setText((String.format(Locale.US, "Your total Earnings: $%.2f", weeklyTotalEarnings)));

        progressBarNecessities.setProgress(50);
        progressBarWants.setProgress(30);
        progressBarSavings.setProgress(20);

    }
}
