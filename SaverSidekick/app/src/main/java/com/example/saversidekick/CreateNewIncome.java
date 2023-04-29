package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;


public class CreateNewIncome extends AppCompatActivity {
    public EditText incomeTypeEditText;
    public EditText newIncomeAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_income);

        incomeTypeEditText = findViewById(R.id.editTextIncomeType);
        newIncomeAmount = findViewById(R.id.editTextNewIncomeAmount);
        Button addIncome = findViewById(R.id.button_AddIncome);

        addIncome.setOnClickListener(view -> {
            String incomeTypeString = incomeTypeEditText.getText().toString();
            String newIncomeAmountString = newIncomeAmount.getText().toString();
            double newIncomeAmount = Double.parseDouble(newIncomeAmountString);

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