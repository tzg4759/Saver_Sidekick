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


    }
}
