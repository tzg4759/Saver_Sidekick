package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextButton = findViewById(R.id.earning_button);
        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EarningsActivity.class);
            startActivity(intent);
        });

        Button viewUpcomingExpense = findViewById(R.id.budge_button);
        viewUpcomingExpense.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, UpcomingBudgetActivity.class);
            startActivity(i);
        });
    }

}
