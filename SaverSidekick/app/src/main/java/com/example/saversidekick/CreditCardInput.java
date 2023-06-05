package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreditCardInput extends AppCompatActivity {
    EditText Cardnumber,Expiredata,cvvnumber;
  //  RadioGroup radioGroup,anz,bnz;
  //  RadioButton radioButton;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_input);
        //get setting
       // radioGroup = findViewById(R.id.RadioGroup);
        Cardnumber=findViewById(R.id.cardnumber);
        Expiredata=findViewById(R.id.Expire);
        cvvnumber=findViewById(R.id.cvv);
        add = findViewById(R.id.add);

        //change to var
        String getNum = Cardnumber.getText().toString();
        String date = Expiredata.getText().toString();
        String getCvv = cvvnumber.getText().toString();

        //credit card
       // CreditCard card = new CreditCard(getNum,date,cvv,"anz");
        //submit
        add.setOnClickListener(view -> {
            Intent intent = new Intent(CreditCardInput.this, DisplayCreditCard.class);

            try {
            if (TextUtils.isEmpty(getNum)||(getNum.length()>8)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct Card number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(date)) {
                Toast.makeText(CreditCardInput.this,"Please enter a Expire data",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getCvv)||(getCvv.length()>3)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct cvv number",Toast.LENGTH_SHORT).show();
                return;
            }
            }catch (NumberFormatException e){

            }
            startActivity(intent);
        });


    }
}