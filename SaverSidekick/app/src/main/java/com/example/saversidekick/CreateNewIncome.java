package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateNewIncome extends AppCompatActivity {
    public EditText incomeTypeEditText;
    public EditText newIncomeAmount;
    public TextView createdIncomeTextView;
    public Button cancelButton;
    public Button addIntoTotalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_income);

        incomeTypeEditText = findViewById(R.id.editTextIncomeType);
        newIncomeAmount = findViewById(R.id.editTextNewIncomeAmount);
        Button addIncome = findViewById(R.id.button_AddIncome);
        createdIncomeTextView = findViewById(R.id.textViewCreatedIncome);
        cancelButton = findViewById(R.id.button_incomeCancel);
        addIntoTotalButton = findViewById(R.id.button_addIntoTotal);

        // Disable the cancel button and addIntoTotal button initially
        cancelButton.setEnabled(false);
        addIntoTotalButton.setEnabled(false);


        addIncome.setOnClickListener(view -> {
            String incomeTypeString = incomeTypeEditText.getText().toString();
            String newIncomeAmountString = newIncomeAmount.getText().toString();
            double newIncomeAmount = Double.parseDouble(newIncomeAmountString);

            // Display the created income details
            String createdIncomeText = "Income Type: " + incomeTypeString + "\nAmount: " + newIncomeAmountString;
            createdIncomeTextView.setText(createdIncomeText);

            // Enable the cancel button and addIntoTotal button
            cancelButton.setEnabled(true);
            addIntoTotalButton.setEnabled(true);

        });

        cancelButton.setOnClickListener(view -> {
            // Clear the created income details
            createdIncomeTextView.setText("");

            // Clear the saved income details from SharedPreferences
            SharedPreferences sharedPreferences_cna = PreferenceManager.getDefaultSharedPreferences(CreateNewIncome.this);
            SharedPreferences.Editor editor = sharedPreferences_cna.edit();
            editor.remove("newIncomeAmount");
            editor.remove("incomeType");
            editor.apply();

        });

        addIntoTotalButton.setOnClickListener(view->{
            String incomeTypeString = incomeTypeEditText.getText().toString();
            String newIncomeAmountString = newIncomeAmount.getText().toString();
            double newIncomeAmount = Double.parseDouble(newIncomeAmountString);

            // Save the new income details to SharedPreferences
            SharedPreferences sharedPreferences_cna = PreferenceManager.getDefaultSharedPreferences(CreateNewIncome.this);
            SharedPreferences.Editor editor = sharedPreferences_cna.edit();
            editor.putFloat("newIncomeAmount",(float)newIncomeAmount);
            editor.putString("incomeType",(String)incomeTypeString);
            editor.apply();

            Intent intent = new Intent(CreateNewIncome.this, HomePageActivity.class);
            startActivity(intent);

            editor.clear();
        });

    }

}