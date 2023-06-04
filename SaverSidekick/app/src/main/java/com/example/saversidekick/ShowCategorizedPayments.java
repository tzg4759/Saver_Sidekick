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
        // Retrieve necessaryTransactions, unnecessaryTransactions, and improvementTransactions from the intent
        necessaryTransactions = getIntent().getParcelableArrayListExtra("necessaryTransactions");
        unnecessaryTransactions = getIntent().getParcelableArrayListExtra("unnecessaryTransactions");
        improvementTransactions = getIntent().getParcelableArrayListExtra("improvementTransactions");
        // Display the categorized transactions
        displayCategorizedTransactions();
    }

    private void displayCategorizedTransactions() {
        // Find the necessaryTitleTextView and necessaryTextView in the layout
        TextView necessaryTitleTextView = findViewById(R.id.necessaryTitleTextView);
        TextView necessaryTextView = findViewById(R.id.necessaryTextView);
        // Set the title and text for the necessary transactions
        necessaryTitleTextView.setText("Necessary Transactions");
        necessaryTextView.setText(getTransactionsSummary(necessaryTransactions));
        // Find the unnecessaryTitleTextView and unnecessaryTextView in the layout
        TextView unnecessaryTitleTextView = findViewById(R.id.unnecessaryTitleTextView);
        TextView unnecessaryTextView = findViewById(R.id.unnecessaryTextView);
        // Set the title and text for the unnecessary transactions
        unnecessaryTitleTextView.setText("Unnecessary Transactions");
        unnecessaryTextView.setText(getTransactionsSummary(unnecessaryTransactions));
        // Find the improvementTitleTextView and improvementTextView in the layout
        TextView improvementTitleTextView = findViewById(R.id.improvementTitleTextView);
        TextView improvementTextView = findViewById(R.id.improvementTextView);
        // Set the title and text for the improvement transactions
        improvementTitleTextView.setText("Transactions Needing Improvement");
        improvementTextView.setText(getTransactionsSummary(improvementTransactions));
    }

    private String getTransactionsSummary(ArrayList<Transaction> transactions) {
        // Create a StringBuilder to store the transaction summary
        StringBuilder summary = new StringBuilder();
        // Iterate through each transaction and append the memo to the summary
        for (Transaction t : transactions) {
            summary.append(t.getMemo()).append("\n");
        }
        // Convert the StringBuilder to a String and return it
        return summary.toString();
    }
}

