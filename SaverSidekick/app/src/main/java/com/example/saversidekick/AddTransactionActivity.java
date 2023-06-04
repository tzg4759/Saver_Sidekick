package com.example.saversidekick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputAmount;
    EditText inputDate;
    boolean expense = true;
    boolean income = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        //UI components
        inputName = (EditText) findViewById(R.id.transactionNameInput);
        inputAmount = (EditText) findViewById(R.id.transactionAmountInput);
        inputDate = (EditText) findViewById(R.id.transactionDateInput);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        inputDate.setText(formattedDate);

        Button transactionInputButton = findViewById(R.id.transactionInputButton);
        transactionInputButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddTransactionActivity.this, HomePageActivity.class);
            String transaction = createTransaction();
            System.out.println(transaction);
            intent.putExtra("transactionString", transaction);
            if (transaction != "")
            {
                Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Button cancelButton = findViewById(R.id.cancelInput);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddTransactionActivity.this, HomePageActivity.class);
            startActivity(intent);
        });
    }

    public String createTransaction() {
        try {
            String name = inputName.getText().toString().trim().replaceAll("\\s", "");
            float amount = Float.valueOf(inputAmount.getText().toString().trim());
            String date = inputDate.getText().toString().trim();

            System.out.println(expense);
            System.out.println(income);

            if (expense == true)
            {
                amount *= -1;
            }

            boolean error = false;
            boolean dateEmpty = false;

            if (date.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Error: Date is empty", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (name.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Error: Name is empty", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (amount > 2147483647)
            {
                Toast.makeText(getApplicationContext(), "Error: Goal values cannot be above 2147483647", Toast.LENGTH_SHORT).show();
                error = true;
            }

            if (!(dateEmpty))
            {
                boolean checkDate = checkDate(date);

                if (checkDate != true)
                {
                    error = true;
                    Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_SHORT).show();

                }
            }

            if (error == false)
            {
                return date+" "+name+" "+amount;
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public boolean checkDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        Date enteredDate;
        try {
            enteredDate = format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_expense:
                if (checked)
                {
                    expense = true;
                    income = false;
                    break;
                }
            case R.id.radio_income:
                if (checked)
                {
                    income = true;
                    expense = false;
                    break;
                }

        }
    }

}


