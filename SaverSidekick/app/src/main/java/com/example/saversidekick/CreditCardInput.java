package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreditCardInput extends AppCompatActivity {
    EditText Cardnumber,Expiredata,cvvnumber;
    //Radio group
    RadioGroup radioGroup;
    RadioButton radioButton;
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
//Radio group
        radioGroup = findViewById(R.id.banks);

        //credit card
       // CreditCard card = new CreditCard(getNum,date,cvv,"anz");
        //submit
        add.setOnClickListener(view -> {
            String getNum = Cardnumber.getText().toString();
            String date = Expiredata.getText().toString();
            String getCvv = cvvnumber.getText().toString();
            Intent intent = new Intent(CreditCardInput.this, DisplayCreditCard.class);
            if (TextUtils.isEmpty(getNum)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct Card number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (getNum.length()<10) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct Card number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(date)) {
                Toast.makeText(CreditCardInput.this,"Please enter a Expire data",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(getCvv)) {
                Toast.makeText(CreditCardInput.this,"Please enter a correct cvv number",Toast.LENGTH_SHORT).show();
                return;
            }
            int radioID = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioID);
            if (radioButton == null|| radioGroup==null) {
                Toast.makeText(CreditCardInput.this,"Please Select ur Bank",Toast.LENGTH_SHORT).show();
                return;
            }
            intent.putExtra("getNum", getNum);
            intent.putExtra("date", date);
            intent.putExtra("getCvv", getCvv);
            intent.putExtra("Bank", radioButton.getText());
            startActivity(intent);
        });


    }
    public void checkButton(View view){
      int radioID = radioGroup.getCheckedRadioButtonId();
      radioButton = findViewById(radioID);
      Toast.makeText(this,"Selected radio"+radioButton.getText(),Toast.LENGTH_SHORT);
    }
}