package com.example.saversidekick;

import java.util.Date;

public class Goal {
    private String name;
    private int goalTotal;
    private int goalCurrent;
    private String date;



    //getters and setters for variables of goal object
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoalTotal() {
        return goalTotal;
    }

    public void setGoalTotal(int goalTotal) {
        this.goalTotal = goalTotal;
    }

    public int getGoalCurrent() {
        return goalCurrent;
    }

    public void setGoalCurrent(int goalCurrent) {
        this.goalCurrent = goalCurrent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //constructor for goal object
    public Goal(String name, int goalTotal, int goalCurrent) {
        this.name = name;
        this.goalTotal = goalTotal;
        this.goalCurrent = goalCurrent;
    }

    public String toString() {
        String output = name+","+goalTotal+","+goalCurrent;
        if (this.date != null)
        {
            output += ","+date;
        }

        return output;
    }


}
