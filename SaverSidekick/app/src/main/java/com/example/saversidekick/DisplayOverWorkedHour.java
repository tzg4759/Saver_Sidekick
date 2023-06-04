package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DisplayOverWorkedHour extends AppCompatActivity {
    Button ToHome,BacktoOWT;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_over_worked_hour);
        Intent intent = getIntent();
        result = findViewById(R.id.CalculationOTW);
        ToHome=findViewById(R.id.ToHomePage);
        BacktoOWT = findViewById(R.id.BacktoOTW);
        String getHour = intent.getStringExtra("getHour");
        String getBonus = intent.getStringExtra("getBonus");
        String income = intent.getStringExtra("income");
        int intHour =Integer.parseInt(getHour);
        int Bonusnum = Integer.parseInt(getBonus);
        int incomenum =Integer.parseInt(income);
        int total = intHour*Bonusnum*incomenum;
        result.setText("OverWorked Hour:"+getHour+"Bonus Ratio:"+ getBonus +"income:"+income+"in total of :" +
                total);
        ToHome.setOnClickListener(view -> {
            Intent i = new Intent(DisplayOverWorkedHour.this, MainActivity.class);
            startActivity(i);
        });
        BacktoOWT.setOnClickListener(view -> {
            Intent i = new Intent(DisplayOverWorkedHour.this, OverTimeHoursWorked.class);
            startActivity(i);
        });
    }
}