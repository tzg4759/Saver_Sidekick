package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
        ProgressBar progressBarNecessities = findViewById(R.id.progressBarNecessities);
        ProgressBar progressBarWants = findViewById(R.id.progressBarWants);
        ProgressBar progressBarSavings = findViewById(R.id.progressBarSavings);

        Button goalsButton = findViewById(R.id.goalsButton);
        goalsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GraphActivity.class);
            startActivity(intent);
        });

        // Retrieve the weekly earnings from SharedPreferences
        double weeklyEarnings = PreferenceManager.getDefaultSharedPreferences(this).getFloat("weeklyEarnings", 0);

        // Calculate the amounts for each category
        double necessities = weeklyEarnings * 0.5;
        double wants = weeklyEarnings * 0.3;
        double savings = weeklyEarnings * 0.2;

        // Update the UI with the calculated amounts
        textViewNeeds.setText(String.format(Locale.US, "Necessities: $%.2f", necessities));
        textViewEverythingElse.setText(String.format(Locale.US, "Wants: $%.2f", wants));
        textViewSavings.setText(String.format(Locale.US, "Savings: $%.2f", savings));
        textViewEarnings.setText((String.format(Locale.US, "Your Earnings: $%.2f", weeklyEarnings)));

        // Calculate the percentage for each category
        double necessitiesPercentage = (necessities / weeklyEarnings) * 100;
        double wantsPercentage = (wants / weeklyEarnings) * 100;
        double savingsPercentage = (savings / weeklyEarnings) * 100;

        // Set the progress of the progress bars
        progressBarNecessities.setProgress((int) necessitiesPercentage);
        progressBarWants.setProgress((int) wantsPercentage);
        progressBarSavings.setProgress((int) savingsPercentage);

        // Change the color of the progress bars based on their progress
        if (necessitiesPercentage < 50) {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (necessitiesPercentage >= 50 && necessitiesPercentage < 70) {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }

        if (wantsPercentage < 30) {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (wantsPercentage >= 30 && wantsPercentage < 70) {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }

        if (savingsPercentage < 20) {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (savingsPercentage >= 20 && savingsPercentage < 50) {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
    }
}
