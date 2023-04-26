package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class CreateGoalActivity  extends AppCompatActivity {

    EditText inputName;
    EditText inputTotal;
    EditText inputCurrent;
    EditText inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        Button createGoalButton = findViewById(R.id.createGoalButton);
        createGoalButton.setOnClickListener(view -> {
            createGoal();
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        inputName = (EditText) findViewById(R.id.inputName);
        inputTotal = (EditText) findViewById(R.id.inputTotal);
        inputCurrent = (EditText) findViewById(R.id.inputCurrent);
        inputDate = (EditText) findViewById(R.id.inputDate);

    }

    public void createGoal() {

        String name = inputName.getText().toString().trim();
        int total = Integer.parseInt(inputTotal.getText().toString());
        int current = Integer.parseInt(inputCurrent.getText().toString());
        String date = inputDate.getText().toString();

        boolean error = false;

        if (name.isEmpty())
        {
            System.out.println("ErrorName");
            error = true;
        }

        if (total <= current)
        {
            System.out.println("Error1");
            error = true;
        }

        if (total <= 0 || current < 0)
        {
            System.out.println("Error2");
            error = true;
        }

        if (!(checkDate(date)))
        {
            System.out.println("Error3");
            error = true;
        }

        if (error == false)
        {
            Goal newGoal = new Goal(name, total, current);
        }

        System.out.println("Test"+name+total+current+date);

    }

    public boolean checkDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        Date enteredDate;

        try {
            enteredDate = format.parse(date);
        } catch (ParseException e) {
            return false;
        }

        Date currentDate = new Date();

        if(enteredDate.after(currentDate))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}
