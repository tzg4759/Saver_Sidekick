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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class GoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        ArrayList<Goal> goalsList = new ArrayList<>();
        String goals = loadGoals("goals.txt");
        System.out.println(goals);

        Button newGoalButton = findViewById(R.id.newGoalButton);
        newGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, CreateGoalActivity.class);
            if (goalsList.size() < 5)
            {
                startActivity(intent);
            }
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        //goalsList UI compononents
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
                date1.setVisibility(View.VISIBLE);
            }
            if (goalsList.size() > 1)
            {
                textGoal2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                progress2.setVisibility(View.VISIBLE);
                date2.setVisibility(View.VISIBLE);
            }
            if (goalsList.size() > 2)
            {
                textGoal3.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.VISIBLE);
                progress3.setVisibility(View.VISIBLE);
                date3.setVisibility(View.VISIBLE);
            }
            if (goalsList.size() > 3)
            {
                textGoal4.setVisibility(View.VISIBLE);
                progressBar4.setVisibility(View.VISIBLE);
                progress4.setVisibility(View.VISIBLE);
                date4.setVisibility(View.VISIBLE);
            }
            if (goalsList.size() > 4)
            {
                textGoal5.setVisibility(View.VISIBLE);
                progressBar5.setVisibility(View.VISIBLE);
                progress5.setVisibility(View.VISIBLE);
                date5.setVisibility(View.VISIBLE);
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

            return new String(content);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
