package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//activity used to edit goals the user has set
public class EditGoalActivity extends AppCompatActivity implements Serializable {

    EditText inputName;
    EditText inputTotal;
    EditText inputCurrent;
    EditText inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        //UI components

        inputName = (EditText) findViewById(R.id.inputName);
        inputTotal = (EditText) findViewById(R.id.inputTotal);
        inputCurrent = (EditText) findViewById(R.id.inputCurrent);
        inputDate = (EditText) findViewById(R.id.inputDate);

        Intent intentBundle = this.getIntent();
        Bundle bundle = intentBundle.getExtras();
        int index = (Integer)bundle.getSerializable("index");
        System.out.println(index);

        ArrayList<Goal> goalsList = new ArrayList<>();
        String goals = loadGoals("goals.txt");

        String[] lines = goals.split(System.getProperty("line.separator"));

        for (String line : lines)
        {
            String[] components = line.split("[|]");
            String name = components[0];
            int total = Integer.parseInt(components[1]);
            int current = Integer.parseInt(components[2]);
            String date = components[3];

            goalsList.add(new Goal(name, total, current, date));
        }

        String name = goalsList.get(index).getName();
        int total = goalsList.get(index).getGoalTotal();
        int current = goalsList.get(index).getGoalCurrent();
        String date = goalsList.get(index).getDate();

        inputName.setText(name);
        inputTotal.setText(Integer.toString(total));
        inputCurrent.setText(Integer.toString(current));

        if (!date.equals("null"))
        {
            inputDate.setText(date);
        }

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button editButton = findViewById(R.id.createGoalButton);
        editButton.setText("Edit Goal");
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditGoalActivity.this, GoalsActivity.class);
            try {
                removeLine(goalsList, index);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (editGoal() == true)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Error: Goal could not be created", Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
        });

        Button deleteButton = findViewById(R.id.deleteGoalButton);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(view -> {
            Intent intent = new Intent(EditGoalActivity.this, GoalsActivity.class);
            try {
                removeLine(goalsList, index);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(getApplicationContext(), "Goal deleted", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    //method to load goals from file
    public String loadGoals(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, fileName);
        byte[] content = new byte[(int) file.length()];
        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(content);
            stream.close();

            return new String(content);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //method to remove line if goal is deleted
    public void removeLine(ArrayList<Goal> goals, int index) throws IOException {
        File path = getApplicationContext().getFilesDir();
        File goalsFile = new File(path, "goals.txt");
        File tempFile = new File(path, "temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(goalsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = goals.get(index).toString();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {

            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        tempFile.renameTo(goalsFile);
    }

    //method to edit goals based on user input
    public boolean editGoal() {
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

    //method to check the date the user has entered
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

    //method to write edited goals to file
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
