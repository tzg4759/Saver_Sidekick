package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SavingSearch extends AppCompatActivity {
    EditText Start, num;
    Button toGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_search);
        toGraph =findViewById(R.id.TotheGraphPageButton);
        Start = findViewById(R.id.StartMonth);
        num = findViewById(R.id.NumMonth);
        Button toEdu =findViewById(R.id.ToEduRef);
        toGraph.setOnClickListener(view -> {
            String getStart = Start.getText().toString();
            String getNumber = num.getText().toString();
            Intent intent = new Intent(SavingSearch.this, SavingsGraph.class);
            //Pass data to 2nd activity
            if (TextUtils.isEmpty(getStart)) {
                Toast.makeText(SavingSearch.this,"Please enter a number in the Start box(1-11)",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getNumber)) {
                Toast.makeText(SavingSearch.this,"Please enter a number in the Number box",Toast.LENGTH_SHORT).show();
                return;
            }
            // changing input to integer
            int Startvalue = 0;
            try {
                Startvalue = Integer.parseInt(Start.getText().toString());
                if(Startvalue<1 ||Startvalue>11){
                    Toast.makeText(SavingSearch.this,"Please enter a number in the Start box(from 1-11)",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            int Numvalue = 0;
            try {
                Numvalue = Integer.parseInt(num.getText().toString());
                if(Numvalue<1 ||Numvalue>6){
                    Toast.makeText(SavingSearch.this,"Please enter a number of month box(from 1-5)",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                // Handle the case where the EditText does not contain a valid integer value
                e.printStackTrace();
            }
            //Get data from input field
            intent.putExtra("start", getStart);
            intent.putExtra("number", getNumber);
            startActivity(intent);
        });
        toEdu.setOnClickListener(view -> {
            Intent intent = new Intent(SavingSearch.this, financial_education_resourse.class);
            startActivity(intent);
        });
    }
}