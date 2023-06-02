package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowCategorizedPayments extends AppCompatActivity {
    private ArrayList<Transaction> necessaryTransactions;
    private ArrayList<Transaction> unnecessaryTransactions;
    private ArrayList<Transaction> improvementTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categorized_payments);
        necessaryTransactions = getIntent().getParcelableArrayListExtra("necessaryTransactions");
        unnecessaryTransactions = getIntent().getParcelableArrayListExtra("unnecessaryTransactions");
        improvementTransactions = getIntent().getParcelableArrayListExtra("improvementTransactions");

        displayCategorizedTransactions();
    }

    private void displayCategorizedTransactions() {
        TextView necessaryTitleTextView = findViewById(R.id.necessaryTitleTextView);
        TextView necessaryTextView = findViewById(R.id.necessaryTextView);
        necessaryTitleTextView.setText("Necessary Transactions");
        necessaryTextView.setText(getTransactionsSummary(necessaryTransactions));

        TextView unnecessaryTitleTextView = findViewById(R.id.unnecessaryTitleTextView);
        TextView unnecessaryTextView = findViewById(R.id.unnecessaryTextView);
        unnecessaryTitleTextView.setText("Unnecessary Transactions");
        unnecessaryTextView.setText(getTransactionsSummary(unnecessaryTransactions));

        TextView improvementTitleTextView = findViewById(R.id.improvementTitleTextView);
        TextView improvementTextView = findViewById(R.id.improvementTextView);
        improvementTitleTextView.setText("Transactions Needing Improvement");
        improvementTextView.setText(getTransactionsSummary(improvementTransactions));
    }

    private String getTransactionsSummary(ArrayList<Transaction> transactions) {
        StringBuilder summary = new StringBuilder();

        for (Transaction t : transactions) {
            summary.append(t.getMemo()).append("\n");
        }

        return summary.toString();
    }
}

