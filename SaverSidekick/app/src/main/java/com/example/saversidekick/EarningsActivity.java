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

        // Find the EditText view with the ID "editTextWeeklyEarnings" and assign it to the weeklyEarningsEditText variable
        weeklyEarningsEditText = findViewById(R.id.editTextWeeklyEarnings);

        // Find the Button view with the ID "buttonSubmitEarnings" and assign it to the submitButton variable
        Button submitButton = findViewById(R.id.buttonSubmitEarnings);

        // Set a click listener for the submitButton
        submitButton.setOnClickListener(view -> {
            // Get the text entered in the weeklyEarningsEditText and convert it to a string
            String weeklyEarningsString = weeklyEarningsEditText.getText().toString();

            if (weeklyEarningsString.isEmpty()) {
                // If the weeklyEarningsString is empty, show an error message in the EditText view
                weeklyEarningsEditText.setError("Please enter your weekly earnings");
            } else {
                try {
                    // Parse the weeklyEarningsString as a double
                    double weeklyEarnings = Double.parseDouble(weeklyEarningsString);

                    // Get the default SharedPreferences instance
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EarningsActivity.this);

                    // Get the SharedPreferences editor
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Put the weeklyEarnings value in the SharedPreferences with the key "weeklyEarnings"
                    editor.putFloat("weeklyEarnings", (float) weeklyEarnings);

                    // Apply the changes to the SharedPreferences
                    editor.apply();

                    // Create an Intent to navigate to the HomePageActivity
                    Intent intent = new Intent(EarningsActivity.this, HomePageActivity.class);
                    intent.putExtra("Overworkedhour", "1");
                    // Start the HomePageActivity
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    // If an invalid number format is entered, show an error message in the EditText view
                    weeklyEarningsEditText.setError("Please enter a valid number");
                }
            }
        });

        // Find the Button view with the ID "button_createNewIncome" and assign it to the createNewIncome variable
        Button createNewIncome = findViewById(R.id.button_createNewIncome);

        // Set a click listener for the createNewIncome button
        createNewIncome.setOnClickListener(view -> {
            // Create an Intent to navigate to the CreateNewIncome activity
            Intent i = new Intent(EarningsActivity.this, CreateNewIncome.class);

            // Start the CreateNewIncome activity
            startActivity(i);
        });

    }
}
