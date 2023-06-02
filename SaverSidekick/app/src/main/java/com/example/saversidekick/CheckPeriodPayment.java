package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CheckPeriodPayment extends AppCompatActivity {
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Button checkButton;
    private TextView resultTextView;
    private SharedPreferences budgetData;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    private ArrayList<Budget_tableRow> entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_period_payment);

        startDateEditText = findViewById(R.id.check_startDate);
        endDateEditText = findViewById(R.id.check_endDate);
        checkButton = findViewById(R.id.checkPaymentButton);
        resultTextView = findViewById(R.id.check_resultView);

        checkButton.setOnClickListener(v -> {
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(CheckPeriodPayment.this, "Your start date/end date cannot be empty! Please enter valid dates!", Toast.LENGTH_SHORT).show();
            } else if (!isValidDateFormat(startDate) || !isValidDateFormat(endDate)) {
                Toast.makeText(CheckPeriodPayment.this, "Invalid date format! Please enter dates in the format dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            }else{
                // Retrieve the stored budget data
                budgetData = getSharedPreferences("Input", 0);

                // Filter the budget data based on the specified period of time
                ArrayList<Budget_tableRow> filteredEntries = filterBudgetData(startDate, endDate);

                // Display the filtered budget data
                displayFilteredData(filteredEntries);}

        });
    }
    private ArrayList<Budget_tableRow> filterBudgetData(String startDate, String endDate) {
        // Retrieve all budget entries
        entries = new ArrayList<>();
        auth = FirebaseAuth.getInstance();          // initialise the firebase authentication
        currentUser = auth.getCurrentUser();
        budgetData = getSharedPreferences("Input" + currentUser.getEmail(), 0);

        int count = budgetData.getInt("entry_count", 0);

        for (int i = 0; i < count; i++)
        {
            String label = budgetData.getString("entry_label_" + i, "");
            int value = budgetData.getInt("entry_value_"+i, 0);
            String date = budgetData.getString("entry_date_"+i,"");
            entries.add(new Budget_tableRow(value, label, date));
        }

        // Filter the entries based on the specified period of time
        ArrayList<Budget_tableRow> filteredEntries = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date startDateObj, endDateObj;

        try {
            startDateObj = dateFormat.parse(startDate);
            endDateObj = dateFormat.parse(endDate);
            for (Budget_tableRow entry : entries) {
                Date entryDate = dateFormat.parse(entry.getDate());
                if (entryDate != null && entryDate.after(startDateObj) && entryDate.before(endDateObj)) {
                    filteredEntries.add(entry);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return filteredEntries;

    }

    private void displayFilteredData(ArrayList<Budget_tableRow> filteredEntries) {
        StringBuilder sb = new StringBuilder();
        for (Budget_tableRow entry : filteredEntries) {
            sb.append(entry.getText()).append("   ").append(entry.getNum()).append("   ").append(entry.getDate()).append("\n");
        }
        resultTextView.setText("\n" + " Searching period upcoming payment is as belows:\n" +sb.toString());
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setLenient(false); // Ensure strict date parsing

        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}

