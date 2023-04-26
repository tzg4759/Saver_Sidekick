package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class CreateGoalActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        Button createGoalButton = findViewById(R.id.createGoalButton);
        createGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGoalActivity.this, GoalsActivity.class);
            startActivity(intent);
        });
    }
}
