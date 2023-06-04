package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DisplayOverWorkedHour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_over_worked_hour);
        Intent intent = getIntent();

        String getHour = intent.getStringExtra("getHour");
        String getBonus = intent.getStringExtra("getBonus");

    }
}