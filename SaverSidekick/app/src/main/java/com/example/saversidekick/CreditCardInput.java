package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CreditCardInput extends AppCompatActivity {
    EditText cardnumber,Expire,cvv;
    RadioGroup radioGroup,anz,bnz;
    RadioButton radioButton;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_input);
        //get settomg
        radioGroup = findViewById(R.id.RadioGroup);
        cardnumber=findViewById(R.id.cardnumber);
        Expire=findViewById(R.id.Expire);
        cvv=findViewById(R.id.cvv);
        add = findViewById(R.id.add);
        //change to var
        String getNum = cardnumber.getText().toString();
        String date = Expire.getText().toString();
        String getCvv = cvv.getText().toString();
        int cvv = Integer.valueOf(getCvv);
        //credit card
        CreditCard CreditCard = new CreditCard(getNum,date,cvv,"anz");
        //submit
        add.setOnClickListener(view -> {
            Intent intent = new Intent(CreditCardInput.this, DisplayCreditCard.class);
            startActivity(intent);
        });


    }
}