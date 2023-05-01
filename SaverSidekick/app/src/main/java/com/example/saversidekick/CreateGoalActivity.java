package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        inputName = (EditText) findViewById(R.id.inputName);
        inputTotal = (EditText) findViewById(R.id.inputTotal);
        inputCurrent = (EditText) findViewById(R.id.inputCurrent);
        inputDate = (EditText) findViewById(R.id.inputDate);

        Button createGoalButton = findViewById(R.id.createGoalButton);
        createGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            if (createGoal() == true)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Error: Goal could not be created", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });
    }

    public boolean createGoal() {
        try {
            String name = inputName.getText().toString().trim();
            int total = Integer.parseInt(inputTotal.getText().toString().trim());
            int current = Integer.parseInt(inputCurrent.getText().toString().trim());
            String date = inputDate.getText().toString().trim();

            boolean error = false;
            boolean dateEmpty = false;

            if (date.isEmpty())
            {
                dateEmpty = true;
            }

            if (name.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Error: Name is empty", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (total <= current)
            {
                Toast.makeText(getApplicationContext(), "Error: Goal total is less than current amount saved", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (total <= 0 || current < 0)
            {
                Toast.makeText(getApplicationContext(), "Error: Goal values cannot be less than 0", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (total > 2147483647 || current > 2147483647)
            {
                Toast.makeText(getApplicationContext(), "Error: Goal values cannot be above 2147483647", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (!(dateEmpty))
            {
                int checkDate = checkDate(date);

                if (checkDate != 0)
                {
                    error = true;

                    if (checkDate == 1)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_SHORT).show();

                    }
                    if (checkDate == 2)
                    {
                        Toast.makeText(getApplicationContext(), "Entered date comes before current date", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            if (error == false)
            {
                //write this to a file
                Goal newGoal = new Goal(name, total, current);
                if (!(dateEmpty))
                {
                    newGoal.setDate(date);
                }
                writeToFile("goals.txt", newGoal.toString()+"\n");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public int checkDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        Date enteredDate;

        try {
            enteredDate = format.parse(date);
        } catch (ParseException e) {
            return 1;
        }

        Date currentDate = new Date();

        if(enteredDate.after(currentDate))
        {
            return 0;
        }
        else
        {
            return 2;
        }

    }

    public void writeToFile(String fileName, String goalString) {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), true);
            writer.write(goalString.getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Goal created", Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
