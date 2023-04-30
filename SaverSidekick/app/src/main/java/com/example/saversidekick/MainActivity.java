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

        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EarningsActivity.class);
            startActivity(intent);
        });
        Button to_graph_page = findViewById(R.id.tothegraph);
        to_graph_page.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SavingsGraph.class);
            startActivity(intent);
        });
        Button Financial = findViewById(R.id.Financial);
        Financial.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FinancialEducationResourse.class);
            startActivity(intent);
        });

    }
}
