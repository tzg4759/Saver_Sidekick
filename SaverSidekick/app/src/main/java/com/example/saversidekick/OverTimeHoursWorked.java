package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OverTimeHoursWorked extends AppCompatActivity {
    EditText Hours, Ratio;
    Button display,change_income;
    String income = "21";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_time_hours_worked);
        Hours=findViewById(R.id.Hours);
        Ratio =findViewById(R.id.BonusRatio);
        display = findViewById(R.id.OTHSubmit);
        change_income = findViewById(R.id.NewIncomeRatio);
        display.setOnClickListener(view -> {
            String getHour = Hours.getText().toString();
            String getBonus = Ratio.getText().toString();
            //Pass data to 2nd activity
            if (TextUtils.isEmpty(getHour)) {
                Toast.makeText(OverTimeHoursWorked.this,"Please enter a Hour",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getBonus)) {
                getBonus = "1";
            }
            // changing input to integer
            int Startvalue = 0;
            try {
                Startvalue = Integer.parseInt(Hours.getText().toString());
                if(Startvalue<0){
                    Toast.makeText(OverTimeHoursWorked.this,"Please enter a Hours worked starting from 1",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //Get data from input field
            Intent intent = new Intent(OverTimeHoursWorked.this, DisplayOverWorkedHour.class);
            intent.putExtra("getHour", getHour);
            intent.putExtra("getBonus", getBonus);
            intent.putExtra("income", income);
            startActivity(intent);
        });
        change_income.setOnClickListener(view -> {
            Intent intent = new Intent(OverTimeHoursWorked.this, finacial_education_resource.class);
            startActivity(intent);
        });
    }
}