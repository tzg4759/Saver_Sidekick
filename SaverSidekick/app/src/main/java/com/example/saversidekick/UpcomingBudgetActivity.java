package com.example.saversidekick;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UpcomingBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_budge);
        getWindow().getDecorView().setBackgroundColor(Color.CYAN);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(500,"Rent/Mortgage"));
        visitors.add(new PieEntry(400,"Groceries"));
        visitors.add(new PieEntry(300,"Petrol/Vehicles"));
        visitors.add(new PieEntry(200,"Others"));
        PieDataSet pieDataSet = new PieDataSet(visitors, "Upcoming Expense");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Upcoming Expense");
        pieChart.animate();

    }

}