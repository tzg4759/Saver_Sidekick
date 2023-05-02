package com.example.saversidekick;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class HomePageActivity extends AppCompatActivity implements Serializable {

    ArrayList<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //test
        transactionList = reloadTransactions();

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
        }
        else
        {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
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

        System.out.println(transactions.size());

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

}
