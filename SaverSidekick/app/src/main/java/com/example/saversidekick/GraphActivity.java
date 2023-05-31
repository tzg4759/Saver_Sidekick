package com.example.saversidekick;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements Serializable {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private int selectedMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        String monthSums = (String) getIntent().getSerializableExtra("monthString");

        BarChart barChart = findViewById(R.id.barChart);

        // Create a description and set its text to the chart description
        Description description = new Description();
        description.setText("");

        // Set the chart description
        barChart.setDescription(description);

        // Create a list of month sums
        ArrayList<Float> monthValues = new ArrayList<>();
        String components[] = monthSums.split("[|]");
        for (int i = 0; i < 12; i++) {
            System.out.println(components[i]);
            monthValues.add(Float.valueOf(components[i]));
        }

        // Prepare data for the bar chart
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < monthValues.size(); i++) {
            entries.add(new BarEntry(i, monthValues.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Spending");
        dataSet.setColor(Color.parseColor("#FFA726"));
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        // Customize x-axis labels
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
        barChart.getXAxis().setGranularity(1f);

        // Set data and refresh the chart
        barChart.setData(barData);
        barChart.invalidate();

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
                    intent = new Intent(GraphActivity.this, GoalsActivity.class);
                    break;
                case R.id.nav_budget:
                    // Handle budget navigation
                    selectedMenuItemId = R.id.nav_budget;  // Update selectedMenuItemId
                    intent = new Intent(GraphActivity.this, BudgetActivity.class);
                    break;
                case R.id.nav_home:
                    selectedMenuItemId = R.id.nav_home;
                    intent = new Intent(GraphActivity.this, HomePageActivity.class);
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
}
