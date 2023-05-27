package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class NextPaymentCycle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_payment_cycle);
        ArrayList<nextpayment> nextpaymentArrayList = new ArrayList<>();
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "nextpayment.txt");

        if (file.isFile()) {
            String payment = loadGoals("nextpayment.txt");

            String[] lines = payment.split(System.getProperty("line.separator"));

            for (String line : lines) {
                String[] components = line.split("[|]");
                String name = components[0];
                if (!name.isEmpty()) {
                    int total = Integer.parseInt(components[1]);
                    int current = Integer.parseInt(components[2]);
                    String date = components[3];

                    nextpaymentArrayList.add(new nextpayment(name, total, date));
                }
            }

        }

        //UI components
        Button newGoalButton = findViewById(R.id.newnextpayment);
        newGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(NextPaymentCycle.this, CreateGoalActivity.class);
            if (nextpaymentArrayList.size() < 3) {
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "You have the maximum amount of goals", Toast.LENGTH_SHORT).show();
            }
        });
//////////////////////////////////////////////////////////////////////
        Button editGoalButton = findViewById(R.id.editpayment);
        editGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(NextPaymentCycle.this, SelectGoalActivity.class);
            if (nextpaymentArrayList.size() > 0) {
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "You do not have any goals", Toast.LENGTH_SHORT).show();
            }
        });


        //goalsList UI components
        TextView payment1 = findViewById(R.id.payment1);

        TextView payment2 = findViewById(R.id.payment2);

        TextView payment3 = findViewById(R.id.payment3);


    }
    //method to load goals from a file
    public String loadGoals(String fileName) {
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
