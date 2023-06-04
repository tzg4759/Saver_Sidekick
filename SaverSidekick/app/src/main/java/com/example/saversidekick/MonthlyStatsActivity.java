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
        TextView averageMonthlyIncome = findViewById(R.id.averageMonthlyIncome);
        TextView averageMonthlyExpense = findViewById(R.id.averageMonthlyExpense);
        TextView averageMonthlyNet = findViewById(R.id.averageMonthlyNet);

        String thisMonth = (String) getIntent().getSerializableExtra("thisMonth");
        String lastMonth = (String) getIntent().getSerializableExtra("lastMonth");
        float allIncome = (Float) getIntent().getSerializableExtra("allIncome");
        float allExpense = (Float) getIntent().getSerializableExtra("allExpense");
        float allNet = (Float) getIntent().getSerializableExtra("allNet");
        allExpense *= -1;

        String[] thisMonthComponents = thisMonth.split(" ");


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

        if (lastMonth != null)
        {
            String[] lastMonthComponents = lastMonth.split(" ");
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
        }

        float incomeComparison = thisMonthIncome - lastMonthIncome;
        String incomeComparisonString;
        if (incomeComparison < 0)
        {
            incomeComparisonString = "- $"+String.format("%.2f", incomeComparison);
            incomeComparisonText.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
             incomeComparisonString = "+ $"+String.format("%.2f", incomeComparison);
             incomeComparisonText.setTextColor(Color.parseColor("#00FF00"));
        }

        float expenseComparison = thisMonthExpense - lastMonthExpense;
        String expenseComparisonString;
        if (incomeComparison < 0)
        {
            expenseComparisonString = "- $"+String.format("%.2f", expenseComparison);
            expenseComparisonText.setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            expenseComparison *= -1;
            expenseComparisonString = "+ $"+String.format("%.2f", expenseComparison);
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

        thisMonthExpense *= -1;
        lastMonthExpense *= -1;

        thisMonthIncomeText.setText("$"+String.format("%.2f", thisMonthIncome));
        thisMonthExpenseText.setText("$"+String.format("%.2f", thisMonthExpense));
        lastMonthIncomeText.setText("$"+String.format("%.2f", lastMonthIncome));
        lastMonthExpenseText.setText("$"+String.format("%.2f", lastMonthExpense));
        incomeComparisonText.setText(incomeComparisonString);
        expenseComparisonText.setText(expenseComparisonString);
        dailySpendTextCurrent.setText("$"+String.format("%.2f", dailyExpenseThisMonth));
        dailySpendLast.setText("$"+String.format("%.2f", dailyExpenseLastMonth));
        averageMonthlyIncome.setText("$"+String.format("%.2f", (allIncome / 12)));
        averageMonthlyExpense.setText("$"+String.format("%.2f", (allExpense / 12)));
        averageMonthlyNet.setText("$"+String.format("%.2f", (allNet / 12)));
    }
}