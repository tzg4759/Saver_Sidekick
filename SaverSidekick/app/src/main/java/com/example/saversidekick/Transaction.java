package com.example.saversidekick;

//transaction object class
public class Transaction {
    private float amount;
    private String date;
    private String memo;

    //constructor methods
    public Transaction(float amount) {
        this.amount = amount;
    }

    public Transaction(String date, String memo, float amount)
    {
        this.date = date;
        this.memo = memo;
        this.amount = amount;
    }

    //method to export object data
    public String export(){
        return date+"|"+memo+"|"+amount+"\n";
    }

    //toString method
    @Override
    public String toString() {
        return date+"\n"+memo+" "+amount+"\n";
    }

    //getters and setters
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
