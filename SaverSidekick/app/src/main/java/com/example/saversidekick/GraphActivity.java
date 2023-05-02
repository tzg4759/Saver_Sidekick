package com.example.saversidekick;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphActivity extends AppCompatActivity implements Serializable {

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
    }
}
