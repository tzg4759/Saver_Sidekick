package com.example.saversidekick;

public class Transaction {
    private float amount;
    private String date;
    private String memo;

    public Transaction(float amount) {
        this.amount = amount;
    }

    public Transaction(String date, String memo, float amount)
    {
        this.date = date;
        this.memo = memo;
        this.amount = amount;
    }

    public String export(){
        return date+"|"+memo+"|"+amount+"\n";
    }

    @Override
    public String toString() {
        return date+"\n"+memo+" "+amount+"\n";
    }

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
