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
  //  RadioGroup radioGroup,anz,bnz;
  //  RadioButton radioButton;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_input);

    }
}