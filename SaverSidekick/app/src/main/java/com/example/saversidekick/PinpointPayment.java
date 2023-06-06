package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PinpointPayment extends AppCompatActivity {

    private ArrayList<Transaction> transactionList;
    private ArrayList<Transaction> necessaryTransactions;
    private ArrayList<Transaction> unnecessaryTransactions;
    private ArrayList<Transaction> improvementTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinpoint_payment);
        // Get the list of transactions passed from the previous activity
        transactionList = getIntent().getParcelableArrayListExtra("transactionList");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        // Iterate through each transaction
        for (Transaction transaction : transactionList) {
            // Check if the transaction is an expense (negative amount)
            if (transaction.getAmount() < 0) {
                String memo = transaction.getMemo();
                float amount = transaction.getAmount();
                // Create a new LinearLayout to hold each transaction item
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                // Create a TextView to display the memo and amount of the transaction
                TextView memoTextView = new TextView(this);
                memoTextView.setText(memo + ": " + amount + ", --");
                memoTextView.setTextSize(16);
                memoTextView.setPadding(14, 14, 14, 14);
                // Create a TextView to display the selected pinpoint option
                TextView selectedOptionTextView = new TextView(this);
                selectedOptionTextView.setTextSize(16);
                selectedOptionTextView.setPadding(14, 14, 14, 14);
                // Create a Button to trigger the pinpoint options dialog
                Button pinpointButton = new Button(this);
                pinpointButton.setText("Pinpoint");
                pinpointButton.setTextColor(Color.WHITE);
                pinpointButton.setPadding(1, 1, 1, 1);
                pinpointButton.setBackgroundColor(Color.parseColor("#a3657b"));

                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                buttonLayoutParams.width = 200;
                buttonLayoutParams.height = 80;

                pinpointButton.setLayoutParams(buttonLayoutParams);


                pinpointButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Show the pinpoint options dialog when the button is clicked
                        showPinpointOptions(selectedOptionTextView, memo);
                    }
                });
                // Add the TextViews and Button to the item layout
                itemLayout.addView(memoTextView);
                itemLayout.addView(selectedOptionTextView);
                itemLayout.addView(pinpointButton);
                // Add the item layout to the main linear layout
                linearLayout.addView(itemLayout);
            }
        }
    }

    private void showPinpointOptions(final TextView selectedOptionTextView, final String memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pinpoint Options")
                .setItems(R.array.pinpoint_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the selected option from the dialog
                        String selectedOption = getResources().getStringArray(R.array.pinpoint_options)[which];
                        selectedOptionTextView.setText(selectedOption);

                        // Find the corresponding Transaction object
                        for (Transaction t : transactionList) {
                            if (t.getMemo().equals(memo)) {
                                t.setOption(selectedOption); // Set the chosen option
                                break;
                            }
                        }
                    }
                });
        builder.create().show();
    }

    public void viewSummaryButtonClicked(View view) {
        // Get the necessary, unnecessary, and improvement transactions
        necessaryTransactions = getTransactionsByOptionAndAmount(transactionList, "Necessary");
        unnecessaryTransactions = getTransactionsByOptionAndAmount(transactionList, "Unnecessary");
        improvementTransactions = getTransactionsByOptionAndAmount(transactionList, "Needs Improvement");

        // Create an intent to navigate to the ShowCategorizedPayments activity
        Intent intent = new Intent(PinpointPayment.this, ShowCategorizedPayments.class);
        intent.putParcelableArrayListExtra("necessaryTransactions", necessaryTransactions);
        intent.putParcelableArrayListExtra("unnecessaryTransactions", unnecessaryTransactions);
        intent.putParcelableArrayListExtra("improvementTransactions", improvementTransactions);
        startActivity(intent);
    }


    private ArrayList<Transaction> getTransactionsByOptionAndAmount(ArrayList<Transaction> transactions, String option) {
        // Create a new ArrayList to store the filtered transactions
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        // Iterate through each transaction
        for (Transaction t : transactions) {
            // Check if the transaction is an expense (negative amount) and has the specified option
            if (t.getAmount() < 0 && t.getOption() != null && t.getOption().equals(option)) {
                // Add the transaction to the filtered list
                filteredTransactions.add(t);
            }
        }
        // Return the filtered list of transactions
        return filteredTransactions;
    }

}














