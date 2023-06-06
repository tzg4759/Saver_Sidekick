package com.example.saversidekick;

public class nextpayment {
    private String name;
    private int payment_amount;
    String date;

    //getters and setters for variables of goal object
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int payment_amount() {
        return payment_amount;
    }

    public void setGoalTotal(int payment_amount) {
        this.payment_amount = payment_amount;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //constructor for goal object

    public nextpayment(String name, int payment_amount, String date) {
        this.name = name;
        this.payment_amount = payment_amount;
        this.date = date;

    }

    //toString method for a goal object to return all the variables of the object
    public String toString() {
        String output = name + "|" + payment_amount;
        if (this.date != null) {
            output += "|" + date;
        } else {
            output += "|null";
        }
        return output;
    }

}
