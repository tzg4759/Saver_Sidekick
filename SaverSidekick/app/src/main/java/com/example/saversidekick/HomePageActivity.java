package com.example.saversidekick;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity {

    ArrayList<Transaction> transactionList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private int selectedMenuItemId;

    private String addedTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize the UI elements
        TextView textViewNeeds = findViewById(R.id.textViewNeeds);
        TextView textViewEverythingElse = findViewById(R.id.textViewWants);
        TextView textViewSavings = findViewById(R.id.textViewSavings);
        TextView textViewEarnings = findViewById(R.id.textViewEarnings);

        TextView textViewWeeklyEarnings = findViewById(R.id.textViewWeeklyEarnings);
        TextView textViewOtherIncome = findViewById(R.id.textViewNewIncome);

        ProgressBar progressBarNecessities = findViewById(R.id.progressBarNecessities);
        ProgressBar progressBarWants = findViewById(R.id.progressBarWants);
        ProgressBar progressBarSavings = findViewById(R.id.progressBarSavings);
        // To the Search page for the liner graph Button
        Button toSavings = findViewById(R.id.SavingHistory);

        toSavings.setOnClickListener(view -> {Intent intent = new Intent(HomePageActivity.this, SavingGraphSearch.class);
            startActivity(intent);
      });
        //To the Search page for the liner graph Button ends
        addedTransaction = (String)getIntent().getSerializableExtra("transactionString");

        if (addedTransaction != "" && addedTransaction != null)
        {
            String[] components = addedTransaction.split(" ");
            Transaction newTransaction = new Transaction(components[0], components[1], Float.valueOf(components[2]));
            writeToFile("transactions.txt", newTransaction);
        }

        transactionList = reloadTransactions();


        TextView summaryTextView = findViewById(R.id.summaryTextView);
        TextView noTransactionsText = findViewById(R.id.noTransactionsText);
        summaryTextView.setKeyListener(null);

        if (transactionList.size() == 0)
        {
            noTransactionsText.setVisibility(View.VISIBLE);
        }
        else
        {
            loadTransactions("transactions.txt");
            updateSummary(transactionList);
        }

        Button addTransactionButton = findViewById(R.id.addTransaction);
        addTransactionButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        Button goalsButton = findViewById(R.id.goalsButton);
        goalsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button budgetButton = findViewById(R.id.budgetButton);
        budgetButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, BudgetActivity.class);
            startActivity(intent);
        });

        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GraphActivity.class);
            intent.putExtra("monthString", monthSums());
            startActivity(intent);
        });

        Button importButton = findViewById(R.id.importFileButton);
        importButton.setOnClickListener(view -> {
            filePicker();
            transactionList = reloadTransactions();
        });

        // Retrieve the weekly earnings from SharedPreferences
        double weeklyEarnings = PreferenceManager.getDefaultSharedPreferences(this).getFloat("weeklyEarnings", 0);
        // retrieve the new income amount from SharedPreferences
        double newIncomeAmount = PreferenceManager.getDefaultSharedPreferences(this).getFloat("newIncomeAmount",0);

        double weeklyTotalEarnings = weeklyEarnings + newIncomeAmount;
        String newIncomeType = PreferenceManager.getDefaultSharedPreferences(this).getString("incomeType","Other Income");

        // Calculate the amounts for each category
        double necessities = weeklyTotalEarnings * 0.5;
        double wants = weeklyTotalEarnings * 0.3;
        double savings = weeklyTotalEarnings * 0.2;

        // Update the UI with the calculated amounts
        textViewNeeds.setText(String.format(Locale.US, "Necessities: $%.2f", necessities));
        textViewEverythingElse.setText(String.format(Locale.US, "Wants: $%.2f", wants));
        textViewSavings.setText(String.format(Locale.US, "Savings: $%.2f", savings));

        textViewWeeklyEarnings.setText(String.format(Locale.US,"Your Weekly Earning: $%.2f", weeklyEarnings));
        textViewOtherIncome.setText(String.format(Locale.US,"Your "+newIncomeType+": $%.2f", newIncomeAmount));
        textViewEarnings.setText((String.format(Locale.US, "Your total Earnings: $%.2f", weeklyTotalEarnings)));

        // Calculate the percentage for each category
        double necessitiesPercentage = (necessities / weeklyTotalEarnings) * 100;
        double wantsPercentage = (wants / weeklyTotalEarnings) * 100;
        double savingsPercentage = (savings / weeklyTotalEarnings) * 100;

        // Set the progress of the progress bars
        progressBarNecessities.setProgress((int) necessitiesPercentage);
        progressBarWants.setProgress((int) wantsPercentage);
        progressBarSavings.setProgress((int) savingsPercentage);

        // Change the color of the progress bars based on their progress
        if (necessitiesPercentage < 50) {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (necessitiesPercentage >= 50 && necessitiesPercentage < 70) {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarNecessities.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }

        if (wantsPercentage < 30) {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (wantsPercentage >= 30 && wantsPercentage < 70) {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarWants.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }

        if (savingsPercentage < 20) {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (savingsPercentage >= 20 && savingsPercentage < 50) {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        } else {
            progressBarSavings.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            Intent intent;
            switch (menuItem.getItemId()) {
                case R.id.nav_goal:
                    // Handle goals navigation
                    selectedMenuItemId = R.id.nav_goal;  // Update selectedMenuItemId
                    intent = new Intent(HomePageActivity.this, GoalsActivity.class);
                    break;
                case R.id.nav_budget:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_budget;  // Update selectedMenuItemId
                    intent = new Intent(HomePageActivity.this, BudgetActivity.class);
                    break;
                case R.id.nav_graph:
                    selectedMenuItemId = R.id.nav_graph;  // Update selectedMenuItemId
                    intent = new Intent(HomePageActivity.this, GraphActivity.class);
                    intent.putExtra("monthString", monthSums());
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
        reloadTransactions();
        saveMonthSums();
    }

    private void updateSelectedMenuItem() {
        navigationView.setCheckedItem(selectedMenuItemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSelectedMenuItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //activity to get results from the filepicker
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri uri = data.getData();

                    csvReader(uri);
                }
                else {
                    Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
                }
            }
    );

    //method to start the filepicker so the user can select a csv file
    public void filePicker() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.addCategory(Intent.CATEGORY_OPENABLE);
        data.setType("*/*");
        data = Intent.createChooser(data, "Select a CSV file");
        activityResultLauncher.launch(data);
    }

    //method to read the contents of a csv file into an arraylist of transactions
    public void csvReader(Uri uri) {

        ArrayList<Transaction> transactionList = new ArrayList<>();
        String date;
        String memo;
        float amount;
        int count = 0;

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                String[] components = line.split(",");
                count++;
                if (count > 2)
                {
                    date = components[0];
                    memo = components[1];
                    amount = Float.valueOf(components[2]);

                    transactionList.add(new Transaction(date, memo, amount));
                }
            }

            reader.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }

        if (transactionList.size() > 0)
        {
            Toast.makeText(this, "Opened file", Toast.LENGTH_SHORT).show();
            for (Transaction t : transactionList)
            {
                writeToFile("transactions.txt", t);
            }
            Intent intent = new Intent(HomePageActivity.this, PinpointPayment.class);
            intent.putParcelableArrayListExtra("transactionList", transactionList);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }

        updateSummary(transactionList);
    }

    //method to update the summary on the home page
    public void updateSummary(ArrayList<Transaction> transactions) {

        TextView summaryTextView = findViewById(R.id.summaryTextView);
        Collections.sort(transactions);

        for (Transaction t : transactions)
        {
            String current = "";
            current += summaryTextView.getText();
            current += t.getDate()+"\n";
            current += t.getMemo()+"\n";
            if (t.getAmount() > 0)
            {
                current += "+ $"+t.getAmount()+"\n";
            }
            else
            {
                current += "- $"+(t.getAmount() * -1)+"\n";

            }
            summaryTextView.setText(current+"-----------------------------------------------------------"+"\n");
        }

    }

    //method to write transactions objects to a file
    public void writeToFile(String fileName, Transaction transaction) {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), true);
            writer.write(transaction.export().getBytes());
            writer.close();

        } catch (Exception e)
        {
            Toast.makeText(this, "Error writing to file.", Toast.LENGTH_SHORT).show();
        }
    }

    //method to load transactions from a file and return as a string
    public String loadTransactions(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, fileName);
        byte[] content = new byte[(int) file.length()];
        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(content);
            stream.close();

            return new String(content);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //method to sum the transactions for each month and return as a string
    public String monthSums() {
        ArrayList<Transaction> transactions = reloadTransactions();

        float jan = 0.0f;
        float feb = 0.0f;
        float mar = 0.0f;
        float apr = 0.0f;
        float may = 0.0f;
        float jun = 0.0f;
        float jul = 0.0f;
        float aug = 0.0f;
        float sep = 0.0f;
        float oct = 0.0f;
        float nov = 0.0f;
        float dec = 0.0f;

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String output = "";

        if (transactions.size() > 0)
        {
            for (Transaction t : transactions)
            {
                String currDate = t.getDate();
                float currAmount = t.getAmount();
                String[] components = currDate.split("/");
                int currYear = Integer.parseInt(components[2]);
                int currMonth = Integer.parseInt(components[1]);

                if (currYear == year)
                {
                    switch (currMonth) {
                        case 1:
                            jan += currAmount;
                            break;
                        case 2:
                            feb += currAmount;
                            break;
                        case 3:
                            mar += currAmount;
                            break;
                        case 4:
                            apr += currAmount;
                            break;
                        case 5:
                            may += currAmount;
                            break;
                        case 6:
                            jun += currAmount;
                            break;
                        case 7:
                            jul += currAmount;
                            break;
                        case 8:
                            aug += currAmount;
                            break;
                        case 9:
                            sep += currAmount;
                            break;
                        case 10:
                            oct += currAmount;
                            break;
                        case 11:
                            nov += currAmount;
                            break;
                        case 12:
                            dec += currAmount;
                            break;
                    }
                }
            }
        }

        output += jan+"|"+feb+"|"+mar+"|"+apr+"|"+may+"|"+jun+"|"+jul+"|"+aug+"|"+sep+"|"+oct+"|"+nov+"|"+dec;
        return output;
    }

    //method to reload the transactions on the home page
    public ArrayList<Transaction> reloadTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "transactions.txt");

        if (file.isFile())
        {
            String transactions = loadTransactions("transactions.txt");
            String[] lines = transactions.split(System.getProperty("line.separator"));

            for (String line : lines)
            {
                String[] components = line.split("[|]");
                String date = components[0];
                String memo = components[1];
                float amount = Float.valueOf(components[2]);

                transactionList.add(new Transaction(date, memo, amount));
            }
        }
        return transactionList;
    }
    public void goToPinpointPayment(View view) {
        Intent intent = new Intent(HomePageActivity.this, PinpointPayment.class);
        intent.putParcelableArrayListExtra("transactionList", transactionList);
        startActivity(intent);
    }
    public void saveMonthSums() {
        String monthSumString = monthSums();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("monthSums", monthSumString);
        editor.apply();
    }
}
