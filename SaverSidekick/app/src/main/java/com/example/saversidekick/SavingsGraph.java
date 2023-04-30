package com.example.saversidekick;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import java.util.ArrayList;
import java.util.List;

public class SavingsGraph extends AppCompatActivity {
    String[] labels = new String[] {"Jan", "Feb", "Mar", "Apr", "May","Jun", "Jul", "Au", "Sep", "Oct","Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_graph);
        // on below line we are initializing our graph view.
        LineChart chart = findViewById(R.id.Graph);
        List<Entry> entries = new ArrayList<Entry>();
        // this will be from the database after it is setup
        entries.add(new Entry(0, 100));
        entries.add(new Entry(1, 200));
        entries.add(new Entry(2, 150));
        entries.add(new Entry(3, 200));
        entries.add(new Entry(4, 210));


        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(5); // set the number of labels to display on the X-axis
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug","Sep","Oct","Nov","Dec"})); // set the labels for the X-axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set the position of the X-axis labels
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh chart
    }
}