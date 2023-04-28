package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class GoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        ArrayList<Goal> goalsList = new ArrayList<>();
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "goals.txt");

        if (file.isFile())
        {
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

        }
        Button newGoalButton = findViewById(R.id.newGoalButton);
        newGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, CreateGoalActivity.class);
            if (goalsList.size() < 5)
            {
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "You have the maximum amount of goals", Toast.LENGTH_SHORT).show();
            }
        });

        Button editGoalButton = findViewById(R.id.editGoalButton);
        editGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, SelectGoalActivity.class);
            if (goalsList.size() > 0)
            {
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "You do not have any goals", Toast.LENGTH_SHORT).show();
            }
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        //goalsList UI components
        TextView textGoal1 = findViewById(R.id.goal0Name);
        ProgressBar progressBar1 = findViewById(R.id.progressBarGoal0);
        TextView progress1 = findViewById(R.id.currentGoal0);
        TextView date1 = findViewById(R.id.dateGoal0);

        TextView textGoal2 = findViewById(R.id.goal1Name);
        ProgressBar progressBar2 = findViewById(R.id.progressBarGoal1);
        TextView progress2 = findViewById(R.id.currentGoal1);
        TextView date2 = findViewById(R.id.dateGoal1);

        TextView textGoal3 = findViewById(R.id.goal2Name);
        ProgressBar progressBar3 = findViewById(R.id.progressBarGoal2);
        TextView progress3 = findViewById(R.id.currentGoal2);
        TextView date3 = findViewById(R.id.dateGoal2);

        TextView textGoal4 = findViewById(R.id.goal3Name);
        ProgressBar progressBar4 = findViewById(R.id.progressBarGoal3);
        TextView progress4 = findViewById(R.id.currentGoal3);
        TextView date4 = findViewById(R.id.dateGoal3);

        TextView textGoal5 = findViewById(R.id.goal4Name);
        ProgressBar progressBar5 = findViewById(R.id.progressBarGoal4);
        TextView progress5 = findViewById(R.id.currentGoal4);
        TextView date5 = findViewById(R.id.dateGoal4);


        if (goalsList.size() == 0)
        {
            TextView textViewNoGoals = findViewById(R.id.textViewNoGoals);
            textViewNoGoals.setVisibility(View.VISIBLE);
        }
        else
        {
            if (goalsList.size() > 0)
            {
                textGoal1.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
                progress1.setVisibility(View.VISIBLE);

                textGoal1.setText(goalsList.get(0).getName());
                progressBar1.setProgress(goalsList.get(0).getProgress());
                progressBar1.setScaleY(1.75f);
                progress1.setText("$"+goalsList.get(0).getGoalCurrent()+" / "+"$"+goalsList.get(0).getGoalTotal());

                if (!goalsList.get(0).getDate().equals("null"))
                {
                    date1.setVisibility(View.VISIBLE);
                    date1.setText("Due by "+goalsList.get(0).getDate());
                }

            }
            if (goalsList.size() > 1)
            {
                textGoal2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                progress2.setVisibility(View.VISIBLE);

                textGoal2.setText(goalsList.get(1).getName());
                progressBar2.setProgress(goalsList.get(1).getProgress());
                progressBar2.setScaleY(1.75f);
                progress2.setText("$"+goalsList.get(1).getGoalCurrent()+" / "+"$"+goalsList.get(1).getGoalTotal());

                if (!goalsList.get(1).getDate().equals("null"))
                {
                    date2.setVisibility(View.VISIBLE);
                    date2.setText("Due by "+goalsList.get(1).getDate());
                }
            }
            if (goalsList.size() > 2)
            {
                textGoal3.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.VISIBLE);
                progress3.setVisibility(View.VISIBLE);

                textGoal3.setText(goalsList.get(2).getName());
                progressBar3.setProgress(goalsList.get(2).getProgress());
                progressBar3.setScaleY(1.75f);
                progress3.setText("$"+goalsList.get(2).getGoalCurrent()+" / "+"$"+goalsList.get(2).getGoalTotal());

                if (!goalsList.get(2).getDate().equals("null"))
                {
                    date3.setVisibility(View.VISIBLE);
                    date3.setText("Due by "+goalsList.get(2).getDate());
                }
            }
            if (goalsList.size() > 3)
            {
                textGoal4.setVisibility(View.VISIBLE);
                progressBar4.setVisibility(View.VISIBLE);
                progress4.setVisibility(View.VISIBLE);

                textGoal4.setText(goalsList.get(3).getName());
                progressBar4.setProgress(goalsList.get(3).getProgress());
                progressBar4.setScaleY(1.75f);
                progress4.setText("$"+goalsList.get(3).getGoalCurrent()+" / "+"$"+goalsList.get(3).getGoalTotal());

                if (!goalsList.get(3).getDate().equals("null"))
                {
                    date4.setVisibility(View.VISIBLE);
                    date4.setText("Due by "+goalsList.get(3).getDate());
                }
            }
            if (goalsList.size() > 4)
            {
                textGoal5.setVisibility(View.VISIBLE);
                progressBar5.setVisibility(View.VISIBLE);
                progress5.setVisibility(View.VISIBLE);

                textGoal5.setText(goalsList.get(4).getName());
                progressBar5.setProgress(goalsList.get(4).getProgress());
                progressBar5.setScaleY(1.75f);
                progress5.setText("$"+goalsList.get(4).getGoalCurrent()+" / "+"$"+goalsList.get(4).getGoalTotal());

                if (!goalsList.get(4).getDate().equals("null"))
                {
                    date5.setVisibility(View.VISIBLE);
                    date5.setText("Due by "+goalsList.get(4).getDate());
                }
            }
        }
    }

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
}
