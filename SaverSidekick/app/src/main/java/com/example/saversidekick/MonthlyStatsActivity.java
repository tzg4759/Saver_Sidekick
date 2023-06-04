package com.example.saversidekick;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonthlyStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_stats);

        Calendar cal = Calendar.getInstance();
        int currentDay = Integer.parseInt(new SimpleDateFormat("dd").format(cal.getTime()));

        TextView thisMonthIncomeText = findViewById(R.id.thisMonthIncome);
        TextView thisMonthExpenseText = findViewById(R.id.thisMonthExpense);
        TextView lastMonthIncomeText = findViewById(R.id.lastMonthIncome);
        TextView lastMonthExpenseText = findViewById(R.id.lastMonthExpense);
        TextView incomeComparisonText = findViewById(R.id.thisMonthIncomeComp);
        TextView expenseComparisonText = findViewById(R.id.thisMonthExpenseComp);
        TextView dailySpendTextCurrent = findViewById(R.id.averageDailySpendThisMonth);
        TextView dailySpendLast = findViewById(R.id.averageDailySpendLastMonth);

        String thisMonth = (String) getIntent().getSerializableExtra("thisMonth");
        String lastMonth = (String) getIntent().getSerializableExtra("lastMonth");

        String[] thisMonthComponents = thisMonth.split(" ");
        String[] lastMonthComponents = lastMonth.split(" ");

        float thisMonthIncome = 0.0f;
        float thisMonthExpense = 0.0f;
        float lastMonthIncome = 0.0f;
        float lastMonthExpense = 0.0f;

        for (String s : thisMonthComponents)
        {
            float curr = Float.parseFloat(s);

            if (curr > 0)
            {
                thisMonthIncome += curr;
            }
            else
            {
                thisMonthExpense += curr;
            }
        }

        for (String s : lastMonthComponents)
        {
            float curr = Float.parseFloat(s);

            if (curr > 0)
            {
                lastMonthIncome += curr;
            }
            else
            {
                lastMonthExpense += curr;
            }
        }

        float incomeComparison = thisMonthIncome - lastMonthIncome;
        String incomeComparisonString;
        if (incomeComparison < 0)
        {
            incomeComparisonString = "- $"+incomeComparison;
            incomeComparisonText.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
             incomeComparisonString = "+ $"+incomeComparison;
             incomeComparisonText.setTextColor(Color.parseColor("#00FF00"));
        }

        float expenseComparison = thisMonthExpense - lastMonthExpense;
        String expenseComparisonString;
        if (incomeComparison < 0)
        {
            expenseComparisonString = "- $"+expenseComparison;
            expenseComparisonText.setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            expenseComparison *= -1;
            expenseComparisonString = "+ $"+expenseComparison;
            expenseComparisonText.setTextColor(Color.parseColor("#FF0000"));
        }

        float dailyExpenseThisMonth = thisMonthExpense/ currentDay;
        if(dailyExpenseThisMonth < 0)
        {
            dailyExpenseThisMonth *= -1;
        }

        float dailyExpenseLastMonth = lastMonthExpense / currentDay;
        if(dailyExpenseLastMonth < 0)
        {
            dailyExpenseLastMonth *= -1;
        }

        thisMonthIncomeText.setText("$"+thisMonthIncome);
        thisMonthExpenseText.setText("$"+thisMonthExpense);
        lastMonthIncomeText.setText("$"+lastMonthIncome);
        lastMonthExpenseText.setText("$"+lastMonthExpense);
        incomeComparisonText.setText(incomeComparisonString);
        expenseComparisonText.setText(expenseComparisonString);
        dailySpendTextCurrent.setText("$"+dailyExpenseThisMonth);
        dailySpendLast.setText("$"+dailyExpenseLastMonth);
    }
}