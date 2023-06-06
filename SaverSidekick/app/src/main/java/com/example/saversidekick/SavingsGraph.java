package com.example.saversidekick;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Random;
public class SavingsGraph extends AppCompatActivity {
    TextView name, number;
    String[] labels = new String[] {"Jan", "Feb", "Mar", "Apr", "May","Jun", "Jul", "Au", "Sep", "Oct","Dec"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random rand = new Random();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_graph);
        Intent intent = getIntent();
        name = findViewById(R.id.textView5);
        number = findViewById(R.id.textView6);
        String getstart = intent.getStringExtra("start");
        String getNumber = intent.getStringExtra("number");
        String getSavings = intent.getStringExtra("Savings");
        //Set Text
        name.setText("you have entered the starting month: "+getstart+"you have entered the num of month: "+ getNumber);
        number.setText("you have entered the Saving for this of month: "+ getSavings);
        int Startnum =Integer.parseInt(getstart)-1;
        int Monthnum = Integer.parseInt(getNumber);
        int Savingnum =Integer.parseInt(getSavings);
        // on below line we are initializing our graph view.
        LineChart chart = findViewById(R.id.Graph);
        List<Entry> entries = new ArrayList<Entry>();
        // this will be from the database after it is setup
        String []finallabel = new String[12];

        for(int i=0;i<Monthnum;i++){
            finallabel[i]=labels[Startnum+i];
            if(i==Monthnum-1){
                boolean finaladd= entries.add(new Entry(i, Savingnum));
            }
            else {
                int int_random = rand.nextInt(300);
                boolean add = entries.add(new Entry(i, int_random));
            }
        }

        //Setting Graph
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(5); // set the number of labels to display on the X-axis
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(finallabel)); // set the labels for the X-axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set the position of the X-axis labels
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh chart
        Button Home = findViewById(R.id.ToHome);
        Home.setOnClickListener(view -> {
            Intent i = new Intent(SavingsGraph.this,HomePageActivity.class);
            startActivity(i);
        });
    }
}