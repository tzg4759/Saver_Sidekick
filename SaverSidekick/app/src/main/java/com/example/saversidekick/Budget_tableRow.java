package com.example.saversidekick;

public class Budget_tableRow {

    private int num;
    private String text;
    private String date;
    public String getDate(){ return date;}
    public void setDate(String date){ this.date = date;}
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Budget_tableRow(int value, String label, String date)
    {
        this.num = value;
        this.text = label;
        this.date = date;
    }
}
