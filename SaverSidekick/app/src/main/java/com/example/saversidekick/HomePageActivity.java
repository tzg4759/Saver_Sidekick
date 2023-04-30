package com.example.saversidekick;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import java.security.spec.ECField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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

        // Initialize the UI elements
        TextView textViewNeeds = findViewById(R.id.textViewNeeds);
        TextView textViewEverythingElse = findViewById(R.id.textViewWants);
        TextView textViewSavings = findViewById(R.id.textViewSavings);
        TextView textViewEarnings = findViewById(R.id.textViewEarnings);
        ProgressBar progressBarNecessities = findViewById(R.id.progressBarNecessities);
        ProgressBar progressBarWants = findViewById(R.id.progressBarWants);
        ProgressBar progressBarSavings = findViewById(R.id.progressBarSavings);
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

        Button goalsButton = findViewById(R.id.goalsButton);
        goalsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GoalsActivity.class);
            startActivity(intent);
        });

        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePageActivity.this, GraphActivity.class);
            startActivity(intent);
        });

        Button importButton = findViewById(R.id.importFileButton);
        importButton.setOnClickListener(view -> {
            filePicker();
        });

        // Retrieve the weekly earnings from SharedPreferences
        double weeklyEarnings = PreferenceManager.getDefaultSharedPreferences(this).getFloat("weeklyEarnings", 0);

        // Calculate the amounts for each category
        double necessities = weeklyEarnings * 0.5;
        double wants = weeklyEarnings * 0.3;
        double savings = weeklyEarnings * 0.2;

        // Update the UI with the calculated amounts
        textViewNeeds.setText(String.format(Locale.US, "Necessities: $%.2f", necessities));
        textViewEverythingElse.setText(String.format(Locale.US, "Wants: $%.2f", wants));
        textViewSavings.setText(String.format(Locale.US, "Savings: $%.2f", savings));
        textViewEarnings.setText((String.format(Locale.US, "Your Earnings: $%.2f", weeklyEarnings)));

        progressBarNecessities.setProgress(50);
        progressBarWants.setProgress(30);
        progressBarSavings.setProgress(20);

    }

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

    public void filePicker() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.addCategory(Intent.CATEGORY_OPENABLE);
        data.setType("*/*");
        data = Intent.createChooser(data, "Select a CSV file");
        activityResultLauncher.launch(data);
    }

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
                if (count > 8)
                {
                    date = components[0];
                    memo = components[5];
                    amount = Float.valueOf(components[6]);

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
        }
        else
        {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }

        for (Transaction t : transactionList)
        {
            System.out.println(t);
        }

        updateSummary(transactionList);
    }

    public void updateSummary(ArrayList<Transaction> transactions) {

        TextView summaryTextView = findViewById(R.id.summaryTextView);

        for (Transaction t : transactions)
        {
            String current = "";
            current += summaryTextView.getText();
            current += t.getDate()+"\n";
            current += t.getMemo()+"\n";
            current += t.getAmount()+"\n";
            summaryTextView.setText(current+"-----------------------------------------------------------"+"\n");
        }
    }

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
}
