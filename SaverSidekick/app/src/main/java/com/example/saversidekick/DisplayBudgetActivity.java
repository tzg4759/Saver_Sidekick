package com.example.saversidekick;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DisplayBudgetActivity extends AppCompatActivity
{
    private SharedPreferences budgetList;
    private ArrayList<Budget_tableRow> entries;
    private TextView textView1;

    private static final String PREFS_NAME = "BudgetInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_budget);



        budgetList = getSharedPreferences(PREFS_NAME, 0);
        entries = new ArrayList<>();

        int count = budgetList.getInt("entry_count", 0);

        for (int i = 0; i < count; i++)
        {
            String label = budgetList.getString("entry_label_" + i, "");
            int value = budgetList.getInt("entry_value_"+i, 0);
            String date = budgetList.getString("entry_date_"+i,"");
            entries.add(new Budget_tableRow(value, label,date));
        }

        textView1 = findViewById(R.id.textView);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++)
        {
            sb.append(entries.get(i).getText()).append("   ").append(entries.get(i).getNum()).append("   ").append(entries.get(i).getDate()).append("\n");
        }
            textView1.setText(sb.toString());
        //build the pie Chart with the data
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> upcomingExpense = new ArrayList<>();
        for (int i = 0; i< count; i++){
            upcomingExpense.add(new PieEntry(entries.get(i).getNum(),entries.get(i).getText()));
        }
        PieDataSet pieDataSet = new PieDataSet(upcomingExpense, "Upcoming Expense");
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

