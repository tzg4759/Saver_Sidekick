package com.example.saversidekick;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BudgetActivity extends AppCompatActivity {

    FirebaseAuth auth;      // firebase authentication
    FirebaseUser currentUser;       // variable to store current user details from firebase
    Button addButton;  // button to add a budget record to the page/shared prefs
    Button clearButton;     // button to clear the budget records/shared prefs
    Button nextButton;      // button the navigate to the next page (UpComingBudgetExpense)
    AlertDialog dialog;     // dialog entered as user input into the application
    LinearLayout layout;       // instance for the page layout container
    private SharedPreferences budgetData;       // shared preferences instance which records will be saved
    private SharedPreferences.Editor editor;        // shared preferences editor instance to alter the records in shared preferences

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private int selectedMenuItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        auth = FirebaseAuth.getInstance();          // initialise the firebase authentication
        currentUser = auth.getCurrentUser();        // fetch the current user
        addButton = findViewById(R.id.addButton);       // initialise the add button
        nextButton = findViewById(R.id.nextButton);     // initialise the next button
        clearButton = findViewById(R.id.clearButton);       // initialise the clear button
        layout = findViewById(R.id.container);          // initialise the layout container

        budgetData = getSharedPreferences("Input" + currentUser.getEmail(), 0);       // retrieve stored shared preferences using prefs_name
        editor = budgetData.edit();         // initialise shared preferences editor

        buildDialog();      // run build dialog function to display a budget entry on the page and save into shared prefs

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();      // click action for add Button adds new entry dialog to the page
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudgetActivity.this, DisplayBudgetActivity.class);     // click action for the next button goes to DisplayBudgetActivity page
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                layout.removeAllViews();    // click action which will clear the data in shared preferences
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String monthSums = (String) getIntent().getSerializableExtra("monthString");
        String thisMonth = (String) getIntent().getSerializableExtra("thisMonth");
        String lastMonth = (String) getIntent().getSerializableExtra("lastMonth");
        float allIncome = (Float) getIntent().getSerializableExtra("allIncome");
        float allExpense = (Float) getIntent().getSerializableExtra("allExpense");
        float allNet = (Float) getIntent().getSerializableExtra("allNet");

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            Intent intent;
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String monthSumString = sharedPreferences.getString("monthSums", "default_value_if_not_found");
            switch (menuItem.getItemId()) {
                case R.id.nav_goal:
                    // Handle goals navigation
                    selectedMenuItemId = R.id.nav_goal;  // Update selectedMenuItemId
                    intent = new Intent(BudgetActivity.this, GoalsActivity.class);
                    intent.putExtra("monthString", monthSums);
                    intent.putExtra("thisMonth", thisMonth);
                    intent.putExtra("lastMonth", lastMonth);
                    intent.putExtra("allNet", allNet);
                    intent.putExtra("allIncome", allIncome);
                    intent.putExtra("allExpense", allExpense);
                    break;
                case R.id.nav_graph:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_graph;  // Update selectedMenuItemId
                    intent = new Intent(BudgetActivity.this, GraphActivity.class);
                    intent.putExtra("monthString", monthSums);
                    intent.putExtra("thisMonth", thisMonth);
                    intent.putExtra("lastMonth", lastMonth);
                    intent.putExtra("allNet", allNet);
                    intent.putExtra("allIncome", allIncome);
                    intent.putExtra("allExpense", allExpense);
                    startActivity(intent);
                    break;
                case R.id.nav_home:
                    selectedMenuItemId = R.id.nav_home;
                    intent = new Intent(BudgetActivity.this, HomePageActivity.class);
                    intent.putExtra("monthString", monthSums);
                    intent.putExtra("thisMonth", thisMonth);
                    intent.putExtra("lastMonth", lastMonth);
                    intent.putExtra("allNet", allNet);
                    intent.putExtra("allIncome", allIncome);
                    intent.putExtra("allExpense", allExpense);
                    break;
                case R.id.nav_creditCard:
                    selectedMenuItemId = R.id.nav_creditCard;
                    intent = new Intent(BudgetActivity.this, CreditCardInput.class);
                    intent.putExtra("monthString", monthSums);
                    intent.putExtra("thisMonth", thisMonth);
                    intent.putExtra("lastMonth", lastMonth);
                    intent.putExtra("allNet", allNet);
                    intent.putExtra("allIncome", allIncome);
                    intent.putExtra("allExpense", allExpense);
                    break;
                // Handle additional navigation items here
                default:
                    return true;
            }
            intent.putExtra("selectedMenuItemId", selectedMenuItemId);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Get the selectedMenuItemId passed from the previous Activity
        Intent intent = getIntent();
        selectedMenuItemId = intent.getIntExtra("selectedMenuItemId", R.id.nav_home);

        updateSelectedMenuItem();
    }

    private void updateSelectedMenuItem() {
        navigationView.setCheckedItem(selectedMenuItemId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //this function will take user input in the form of dialog and add this input to a card which can be displayed on the view
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.budget_input, null);

        EditText expenseName =  view.findViewById(R.id.expenseNameEdit);        // saves expense name user input into variable
        EditText expenseAmount =  view.findViewById(R.id.expenseAmountEdit);       // saves expense amount user input into variable
        EditText expenseDate = view.findViewById(R.id.expenseDateEdit); // saves expense date user input into variable

        builder.setView(view);
        builder.setTitle("Enter Budgeted Expense Name & Amount:")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCard(expenseName.getText().toString(), "$" + expenseAmount.getText().toString() + " p/w", "Payment Due: " + expenseDate.getText().toString());      // adds new card to the view so record is displayed on page
                        int count = budgetData.getInt("entry_count", 0);        // create count int which tells program where the last saved record is in shared prefs
                        editor.putString("entry_label_" + count, expenseName.getText().toString());     // save expense name user input into shared preferences
                        editor.putInt("entry_value_" + count, Integer.parseInt(expenseAmount.getText().toString()));        // save expense amount user input into shared prefs
                        editor.putString("entry_date_" +count, expenseDate.getText().toString());  //save expense date user input
                        editor.putInt("entry_count", count + 1);        // update the count in shared prefs after new data is added
                        editor.apply();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(BudgetActivity.this, "Error: Budget entry not created/saved. Please try again", Toast.LENGTH_SHORT).show();      // error message if cannot save or create the budget list card
                    }
                });

        dialog = builder.create();
    }

    //function adds a budget expense card object to the page which displays the entries back to the user
    private void addCard(String name, String amount, String date) {
        View view = getLayoutInflater().inflate(R.layout.budget_card, null);

        TextView nameView = view.findViewById(R.id.expenseName);        // initialise expense name input variable
        TextView amountView = view.findViewById(R.id.expenseAmount);    // initialise expense amount input variable
        TextView dateView = view.findViewById(R.id.expensePayDate);
        Button delete = view.findViewById(R.id.deleteButton1);      // initialise delete record button for each card entry

        nameView.setText(name);     // set the card's expense name
        amountView.setText(amount);     // set the card's expense amount
        dateView.setText(date);     // set the card's expense date

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
        SharedPreferences budgetData = getSharedPreferences("Input" + currentUser.getEmail(), 0);     // access user shared prefs instance
        SharedPreferences.Editor editor = budgetData.edit(); // access the shared pref editor
        editor.clear();     // clear shared preferences
        editor.apply();     // apply changes to shared preferences
    }

    // this function restores the saved expense cards to the budget page's view
    protected void onResume()
    {
        super.onResume();
        layout.removeAllViews();    // clear the current cards on the view if any

        budgetData = getSharedPreferences("Input" + currentUser.getEmail(), 0);       // access user shared preferences
        int count = budgetData.getInt("entry_count", 0);        // get array size of shared preferences

        for (int i = 0; i < count; i++)     // loop through the shared prefs array to save/retrieve the data
        {
            String label = budgetData.getString("entry_label_" + i, "");       // add expense name to variable
            int value = budgetData.getInt("entry_value_"+i, 0);     // add expense amount to a variable
            String date = budgetData.getString("entry_date_"+i, "");        // add expense date to a variable
            addCard(label, "$" + value + " p/w", "Payment Due: " + date);     // add card to the budget page with shared preferences data
        }

    }

}