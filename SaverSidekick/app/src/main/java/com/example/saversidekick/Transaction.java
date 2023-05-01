package com.example.saversidekick;

public class Transaction {
    private float amount;

    public Transaction(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
