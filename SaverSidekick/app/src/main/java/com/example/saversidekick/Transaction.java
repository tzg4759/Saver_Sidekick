package com.example.saversidekick;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//transaction object class
public class Transaction implements Parcelable, Comparable<Transaction> {
    private float amount;
    private String date;
    private String memo;
    private String option;

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
    // Parcelable constructor
    protected Transaction(Parcel in) {
        amount = in.readFloat();
        date = in.readString();
        memo = in.readString();
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
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(amount);
        dest.writeString(date);
        dest.writeString(memo);
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int compareTo(Transaction transaction) {
        try {
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(this.date);
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(transaction.date);
            return date2.compareTo(date1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
