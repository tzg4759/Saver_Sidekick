package com.example.saversidekick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SelectGoalActivity extends AppCompatActivity implements Serializable {

    FirebaseAuth auth;
    FirebaseUser currentUser;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        String username = currentUser.getEmail();
        filename = username+"goals.txt";

        ArrayList<Goal> goalsList = new ArrayList<>();
        String goals = loadGoals(filename);

        String[] lines = goals.split(System.getProperty("line.separator"));

        for (String line : lines)
        {
            String[] components = line.split("[|]");
            String name = components[0];
            int total = Integer.parseInt(components[1]);
            int current = Integer.parseInt(components[2]);
            String date = components[3];

            goalsList.add(new Goal(name, total, current, date));
        }

        String monthSums = (String) getIntent().getSerializableExtra("monthString");
        String thisMonth = (String) getIntent().getSerializableExtra("thisMonth");
        String lastMonth = (String) getIntent().getSerializableExtra("lastMonth");
        float allIncome = (Float) getIntent().getSerializableExtra("allIncome");
        float allExpense = (Float) getIntent().getSerializableExtra("allExpense");
        float allNet = (Float) getIntent().getSerializableExtra("allNet");

        Bundle bundle = new Bundle();

        Button goal1Button = findViewById(R.id.goal1Button);
        goal1Button.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, EditGoalActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            bundle.putSerializable("index", 0);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button goal2Button = findViewById(R.id.goal2Button);
        goal2Button.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, EditGoalActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            bundle.putSerializable("index", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button goal3Button = findViewById(R.id.goal3Button);
        goal3Button.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, EditGoalActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            bundle.putSerializable("index", 2);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button goal4Button = findViewById(R.id.goal4Button);
        goal4Button.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, EditGoalActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            bundle.putSerializable("index", 3);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button goal5Button = findViewById(R.id.goal5Button);
        goal5Button.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, EditGoalActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            bundle.putSerializable("index", 4);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        Button cancelButton = findViewById(R.id.cancelSelectionButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(SelectGoalActivity.this, GoalsActivity.class);
            intent.putExtra("monthString", monthSums);
            intent.putExtra("thisMonth", thisMonth);
            intent.putExtra("lastMonth", lastMonth);
            intent.putExtra("allIncome", allIncome);
            intent.putExtra("allExpense", allExpense);
            intent.putExtra("allNet", allNet);
            startActivity(intent);
        });

        if (goalsList.size() > 0)
        {
            goal1Button.setVisibility(View.VISIBLE);
            goal1Button.setText(goalsList.get(0).getName());
        }

        if (goalsList.size() > 1)
        {
            goal2Button.setVisibility(View.VISIBLE);
            goal2Button.setText(goalsList.get(1).getName());
        }

        if (goalsList.size() > 2)
        {
            goal3Button.setVisibility(View.VISIBLE);
            goal3Button.setText(goalsList.get(2).getName());
        }

        if (goalsList.size() > 3)
        {
            goal4Button.setVisibility(View.VISIBLE);
            goal4Button.setText(goalsList.get(3).getName());
        }

        if (goalsList.size() > 4)
        {
            goal5Button.setVisibility(View.VISIBLE);
            goal5Button.setText(goalsList.get(4).getName());
        }
    }

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