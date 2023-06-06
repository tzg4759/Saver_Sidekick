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
        // Showing on the GETing the data
        result = findViewById(R.id.CalculationOTW);
        ToHome=findViewById(R.id.ToHomePage);
        BacktoOWT = findViewById(R.id.BacktoOTW);
        // geting value
        String getHour = intent.getStringExtra("getHour");
        String getBonus = intent.getStringExtra("getBonus");
        String income = intent.getStringExtra("income");
        // String to Float
        float intHour =Float.parseFloat(getHour);
        float Bonusnum = Float.parseFloat(getBonus);
        float incomenum =Float.parseFloat(income);
        double total = intHour*Bonusnum*incomenum;
        // Showing on the textfeild
        result.setText("Extra Hour: "+getHour+" X Bonus Ratio: "+ getBonus +" X income: "+income + "=  in total of :"
                +" Total of "+total);
        ToHome.setOnClickListener(view -> {
            Intent i = new Intent(DisplayOverWorkedHour.this, HomePageActivity.class);
            i.putExtra("Overworkedhour", String.valueOf(total));
            startActivity(i);
        });
        BacktoOWT.setOnClickListener(view -> {
            Intent i = new Intent(DisplayOverWorkedHour.this, OverTimeHoursWorked.class);
            intent.putExtra("income", income);
            startActivity(i);
        });
    }
}