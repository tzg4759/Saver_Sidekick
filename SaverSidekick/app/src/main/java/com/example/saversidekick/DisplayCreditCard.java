package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayCreditCard extends AppCompatActivity {
    TextView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreditCard creditCard = new CreditCard();
        setContentView(R.layout.activity_display_credit_card);
        Intent i = getIntent();
        //Get Data
        String Num = i.getStringExtra("getNum");
        String thedate = i.getStringExtra("date");
        String Bank = i.getStringExtra("Bank");
        Button Home = findViewById(R.id.ToHome);
        Button terms = findViewById(R.id.Terms);
        String settext ="\"Credit Card Number: \n+Num+\n Bank : \n+Bank+\n ExpireDate : \n+ thedate+\n CVV : ***\n";
        main=findViewById(R.id.CreditCardDetail);
        main.setText(settext);
        //Seting the creditcard
        creditCard.setBank(Num);
        creditCard.setBank(Bank);
        creditCard.setDate(thedate);
        Home.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayCreditCard.this,HomePageActivity.class);
            startActivity(intent);
        });
        //Setting the terms and policy button depending on the bank user chosen
        if(Bank.contains("bnz")){
            terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bnz.co.nz/assets/about-us/public-notices/BNZ-credit-card-terms-and-conditions-8-may-2023.pdf?5b498c4c296ac846fd0a621f9c6b5a214f2c26ea"));
                    startActivity(onClickIntent);
                }
            });
        }
        else{
            terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.anz.co.nz/rates-fees-agreements/general-terms-conditions/"));
                    startActivity(onClickIntent);
                }
            });
        }

    }
}