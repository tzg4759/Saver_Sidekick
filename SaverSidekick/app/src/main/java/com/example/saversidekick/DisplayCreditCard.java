package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DisplayCreditCard extends AppCompatActivity {
    TextView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_credit_card);
        Intent i = getIntent();
        String Num = i.getStringExtra("getNum");
        String thedate = i.getStringExtra("date");
        String Bank = i.getStringExtra("Bank");
        Button Home = findViewById(R.id.ToHome);
        Button terms = findViewById(R.id.Terms);
        main=findViewById(R.id.CreditCardDetail);
        main.setText("Credit Card Number: "+Num+"\n Bank : "+Bank+"\n ExpireDate : "+ thedate+"\n CVV : ***");

        Home.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayCreditCard.this,HomePageActivity.class);
            startActivity(intent);
        });
        if(Bank=="bnz"){
            terms.setOnClickListener(view -> {
                Intent intent = new Intent(DisplayCreditCard.this,GoalsActivity.class);
                intent.putExtra("Bank", Bank);
                startActivity(intent);
            });
        }
        else{

        }
        terms.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayCreditCard.this,GoalsActivity.class);
            intent.putExtra("Bank", Bank);
            startActivity(intent);
        });
    }
}