package com.example.saversidekick;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BudgetActivity extends AppCompatActivity {

    Button addBudgetButton;  // button to add a budget record to the page/shared prefs
    Button clearButton;     // button to clear the budget records/shared prefs
    Button displayUpcomingBudgetButton;      // button the navigate to the next page (UpComingBudgetExpense)
    AlertDialog dialog;     // dialog entered as user input into the application
    LinearLayout layout;       // instance for the page layout container
    private SharedPreferences budgetData;       // shared preferences instance which records will be saved
    private SharedPreferences.Editor editor;        // shared preferences editor instance to alter the records in shared preferences
    private static final String PREFS_NAME = "BudgetInput";  // shared preferences label


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        addBudgetButton = findViewById(R.id.addBudgetButton);       // initialise the add button
        displayUpcomingBudgetButton = findViewById(R.id.viewUpcomingBudgetButton);     // initialise the next button
        clearButton = findViewById(R.id.clearButton);       // initialise the clear button
        layout = findViewById(R.id.container);

        budgetData = getSharedPreferences(PREFS_NAME, 0);       // retrieve stored shared preferences using prefs_name
        editor = budgetData.edit();         // initialise shared preferences editor

        buildDialog();      // run build dialog function to display a budget entry on the page and save into shared prefs

        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }       // click action for add Button adds new entry dialog to the page
        });

        displayUpcomingBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE DISPLAY ACTIVITY TO UPCOMINGBUDGETACTIVITY
                Intent intent = new Intent(BudgetActivity.this, DisplayBudgetActivity.class);     // click action for the next button goes to UpcomingBudgetActivity page
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }       // click action which will clear the data in shared preferences
        });
    }
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        EditText expenseName =  view.findViewById(R.id.expenseNameEdit);        // saves expense name user input into variable
        EditText expenseAmount =  view.findViewById(R.id.expenseAmountEdit);       // saves expense amount user input into variable
        EditText expensePayDate = view.findViewById(R.id.expensePayDate); // saves expense amount user input into variable
        builder.setView(view);
        builder.setTitle("Enter Budgeted Expense:")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCard(expenseName.getText().toString(), expenseAmount.getText().toString(), expensePayDate.getText().toString());      // adds new card to the view so record is displayed on page
                        int count = budgetData.getInt("entry_count", 0);        // create count int which tells program where the last saved record is in shared prefs
                        editor.putString("entry_label_" + count, expenseName.getText().toString());     // save expense name user input into shared preferences
                        editor.putInt("entry_value_" + count, Integer.parseInt(expenseAmount.getText().toString()));        // save expense amount user input into shared prefs
                        editor.putString("entry_date_" +count,expensePayDate.getText().toString());  //save expense date user input
                        editor.putInt("entry_count", count + 1);        // update the count in shared prefs after new data is added
                        editor.apply();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // insert error message here
                    }
                });

        dialog = builder.create();
    }

    //function adds a budget expense card object to the page which displays the entries back to the user
    private void addCard(String name, String amount, String payDate) {
        View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.expenseName);        // initialise expense name input variable
        TextView amountView = view.findViewById(R.id.expenseAmount);    // initialise expense amount input variable
        TextView payDateView = view.findViewById(R.id.expensePayDate);// initialise expense pay date
        Button delete = view.findViewById(R.id.deleteButton1);      // initialise delete record button for each card entry

        nameView.setText(name);     // set the card's expense name
        amountView.setText(amount);     // set the card's expense amount
        payDateView.setText(payDate);  // set the card's pay date

        // on click action for the delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }   // sets on click action for the delete button to remove a budget entry card
        });

        layout.addView(view);       // update the view
    }

    // function for the clear data button where a user can clear the data in the arraylist/budget table
    private void clearData()
    {
        SharedPreferences budgetData = getSharedPreferences(PREFS_NAME, 0);     // access shared prefs instance
        SharedPreferences.Editor editor = budgetData.edit(); // access the shared pref editor
        editor.clear();     // clear shared preferences
        editor.apply();     // apply changes to shared preferences
    }
}