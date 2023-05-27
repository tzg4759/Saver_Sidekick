package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        transactionList = getIntent().getParcelableArrayListExtra("transactionList");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        for (Transaction transaction : transactionList) {
            if (transaction.getAmount() < 0) {
                String memo = transaction.getMemo();
                float amount = transaction.getAmount();

                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView memoTextView = new TextView(this);
                memoTextView.setText(memo + ": " + amount + ", --");
                memoTextView.setTextSize(16);
                memoTextView.setPadding(8, 8, 8, 8);

                TextView selectedOptionTextView = new TextView(this);
                selectedOptionTextView.setTextSize(16);
                selectedOptionTextView.setPadding(8, 8, 8, 8);

                Button pinpointButton = new Button(this);
                pinpointButton.setText("Pinpoint");
                pinpointButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPinpointOptions(selectedOptionTextView, memo);
                    }
                });

                itemLayout.addView(memoTextView);
                itemLayout.addView(selectedOptionTextView);
                itemLayout.addView(pinpointButton);

                linearLayout.addView(itemLayout);
            }
        }
    }

    private void showPinpointOptions(final TextView selectedOptionTextView, final String memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pinpoint Options")
                .setItems(R.array.pinpoint_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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
        ArrayList<Transaction> necessaryTransactions = getTransactionsByOptionAndAmount(transactionList, "Necessary");
        ArrayList<Transaction> unnecessaryTransactions = getTransactionsByOptionAndAmount(transactionList, "Unnecessary");
        ArrayList<Transaction> improvementTransactions = getTransactionsByOptionAndAmount(transactionList, "Needs Improvement");

        Intent intent = new Intent(PinpointPayment.this, ShowCategorizedPayments.class);
        intent.putParcelableArrayListExtra("necessaryTransactions", necessaryTransactions);
        intent.putParcelableArrayListExtra("unnecessaryTransactions", unnecessaryTransactions);
        intent.putParcelableArrayListExtra("improvementTransactions", improvementTransactions);
        startActivity(intent);
    }


    private ArrayList<Transaction> getTransactionsByOptionAndAmount(ArrayList<Transaction> transactions, String option) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t.getAmount() < 0 && t.getOption() != null && t.getOption().equals(option)) {
                filteredTransactions.add(t);
            }
        }

        return filteredTransactions;
    }

}














