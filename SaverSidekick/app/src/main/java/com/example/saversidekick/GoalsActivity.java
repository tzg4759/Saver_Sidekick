package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class GoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        Button newGoalButton = findViewById(R.id.newGoalButton);
        newGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, CreateGoalActivity.class);
            startActivity(intent);
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        ArrayList<Goal> goalsList = new ArrayList<Goal>();

        if (goalsList.size() == 0)
        {
            TextView textViewNoGoals = findViewById(R.id.textViewNoGoals);
            textViewNoGoals.setText("You do not have any goals set");
        }
    }
}
