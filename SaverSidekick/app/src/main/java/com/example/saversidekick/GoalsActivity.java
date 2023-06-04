package com.example.saversidekick;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//activity displays all current goals the user has set
public class GoalsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private int selectedMenuItemId;

    FirebaseAuth auth;
    FirebaseUser currentUser;
    String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        String username = currentUser.getEmail();
        filename = username+"goals.txt";

        //read file if it exists

        ArrayList<Goal> goalsList = new ArrayList<>();
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, filename);

        if (file.isFile())
        {
            String goals = loadGoals(filename);

            String[] lines = goals.split(System.getProperty("line.separator"));

            for (String line : lines)
            {
                String[] components = line.split("[|]");
                String name = components[0];
                if (!name.isEmpty())
                {
                    int total = Integer.parseInt(components[1]);
                    int current = Integer.parseInt(components[2]);
                    String date = components[3];

                    goalsList.add(new Goal(name, total, current, date));
                }
            }

        }

        //UI components
        Button newGoalButton = findViewById(R.id.newGoalButton);
        newGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, CreateGoalActivity.class);
            if (goalsList.size() < 5)
            {
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "You have the maximum amount of goals", Toast.LENGTH_SHORT).show();
            }
        });

        Button editGoalButton = findViewById(R.id.editGoalButton);
        editGoalButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, SelectGoalActivity.class);
            if (goalsList.size() > 0)
            {
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "You do not have any goals", Toast.LENGTH_SHORT).show();
            }
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GoalsActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        //goalsList UI components
        TextView textGoal1 = findViewById(R.id.goal0Name);
        ProgressBar progressBar1 = findViewById(R.id.progressBarGoal0);
        TextView progress1 = findViewById(R.id.currentGoal0);
        TextView date1 = findViewById(R.id.dateGoal0);

        TextView textGoal2 = findViewById(R.id.goal1Name);
        ProgressBar progressBar2 = findViewById(R.id.progressBarGoal1);
        TextView progress2 = findViewById(R.id.currentGoal1);
        TextView date2 = findViewById(R.id.dateGoal1);

        TextView textGoal3 = findViewById(R.id.goal2Name);
        ProgressBar progressBar3 = findViewById(R.id.progressBarGoal2);
        TextView progress3 = findViewById(R.id.currentGoal2);
        TextView date3 = findViewById(R.id.dateGoal2);

        TextView textGoal4 = findViewById(R.id.goal3Name);
        ProgressBar progressBar4 = findViewById(R.id.progressBarGoal3);
        TextView progress4 = findViewById(R.id.currentGoal3);
        TextView date4 = findViewById(R.id.dateGoal3);

        TextView textGoal5 = findViewById(R.id.goal4Name);
        ProgressBar progressBar5 = findViewById(R.id.progressBarGoal4);
        TextView progress5 = findViewById(R.id.currentGoal4);
        TextView date5 = findViewById(R.id.dateGoal4);

        //set components visible if goal objects exist
        if (goalsList.size() == 0)
        {
            TextView textViewNoGoals = findViewById(R.id.textViewNoGoals);
            textViewNoGoals.setVisibility(View.VISIBLE);
        }
        else
        {
            if (goalsList.size() > 0)
            {
                textGoal1.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.VISIBLE);
                progress1.setVisibility(View.VISIBLE);

                textGoal1.setText(goalsList.get(0).getName());
                progressBar1.setProgress(goalsList.get(0).getProgress());
                progressBar1.setScaleY(1.75f);
                progress1.setText("$"+goalsList.get(0).getGoalCurrent()+" / "+"$"+goalsList.get(0).getGoalTotal());

                if (!goalsList.get(0).getDate().equals("null"))
                {
                    date1.setVisibility(View.VISIBLE);
                    date1.setText("Due by "+goalsList.get(0).getDate());
                }

            }
            if (goalsList.size() > 1)
            {
                textGoal2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                progress2.setVisibility(View.VISIBLE);

                textGoal2.setText(goalsList.get(1).getName());
                progressBar2.setProgress(goalsList.get(1).getProgress());
                progressBar2.setScaleY(1.75f);
                progress2.setText("$"+goalsList.get(1).getGoalCurrent()+" / "+"$"+goalsList.get(1).getGoalTotal());

                if (!goalsList.get(1).getDate().equals("null"))
                {
                    date2.setVisibility(View.VISIBLE);
                    date2.setText("Due by "+goalsList.get(1).getDate());
                }
            }
            if (goalsList.size() > 2)
            {
                textGoal3.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.VISIBLE);
                progress3.setVisibility(View.VISIBLE);

                textGoal3.setText(goalsList.get(2).getName());
                progressBar3.setProgress(goalsList.get(2).getProgress());
                progressBar3.setScaleY(1.75f);
                progress3.setText("$"+goalsList.get(2).getGoalCurrent()+" / "+"$"+goalsList.get(2).getGoalTotal());

                if (!goalsList.get(2).getDate().equals("null"))
                {
                    date3.setVisibility(View.VISIBLE);
                    date3.setText("Due by "+goalsList.get(2).getDate());
                }
            }
            if (goalsList.size() > 3)
            {
                textGoal4.setVisibility(View.VISIBLE);
                progressBar4.setVisibility(View.VISIBLE);
                progress4.setVisibility(View.VISIBLE);

                textGoal4.setText(goalsList.get(3).getName());
                progressBar4.setProgress(goalsList.get(3).getProgress());
                progressBar4.setScaleY(1.75f);
                progress4.setText("$"+goalsList.get(3).getGoalCurrent()+" / "+"$"+goalsList.get(3).getGoalTotal());

                if (!goalsList.get(3).getDate().equals("null"))
                {
                    date4.setVisibility(View.VISIBLE);
                    date4.setText("Due by "+goalsList.get(3).getDate());
                }
            }
            if (goalsList.size() > 4)
            {
                textGoal5.setVisibility(View.VISIBLE);
                progressBar5.setVisibility(View.VISIBLE);
                progress5.setVisibility(View.VISIBLE);

                textGoal5.setText(goalsList.get(4).getName());
                progressBar5.setProgress(goalsList.get(4).getProgress());
                progressBar5.setScaleY(1.75f);
                progress5.setText("$"+goalsList.get(4).getGoalCurrent()+" / "+"$"+goalsList.get(4).getGoalTotal());

                if (!goalsList.get(4).getDate().equals("null"))
                {
                    date5.setVisibility(View.VISIBLE);
                    date5.setText("Due by "+goalsList.get(4).getDate());
                }
            }
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
                case R.id.nav_graph:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_graph;  // Update selectedMenuItemId
                    intent = new Intent(GoalsActivity.this, GraphActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String monthSumString = sharedPreferences.getString("monthSums", "default_value_if_not_found");
                    intent.putExtra("monthString", monthSumString);
                    startActivity(intent);
                    break;
                case R.id.nav_budget:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_budget;  // Update selectedMenuItemId
                    intent = new Intent(GoalsActivity.this, BudgetActivity.class);
                    break;
                case R.id.nav_home:
                    selectedMenuItemId = R.id.nav_home;
                    intent = new Intent(GoalsActivity.this, HomePageActivity.class);
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
    protected void onResume() {
        super.onResume();
        updateSelectedMenuItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
