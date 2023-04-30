package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinancialEducationResourse extends AppCompatActivity {

    Button Politechic;
    Button MoneyHub;
    Button ReservedBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_education_resourse);
        Politechic = findViewById(R.id.Politechic);
        MoneyHub=findViewById(R.id.MoneyHub);
        ReservedBank=findViewById(R.id.ReservedBank);
        Politechic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.openpolytechnic.ac.nz/qualifications-and-courses/fou103-financial-skills-for-life/"));
            startActivity(onClickIntent);
            }
        });
        MoneyHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.moneyhub.co.nz/free-financial-literacy-classes.html"));
                startActivity(onClickIntent);
            }
        });
        ReservedBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.rbnz.govt.nz/education"));
                startActivity(onClickIntent);
            }
        });


    }
}