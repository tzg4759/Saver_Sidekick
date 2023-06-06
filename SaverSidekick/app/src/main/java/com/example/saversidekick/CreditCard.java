package com.example.saversidekick;

public class CreditCard {
    private String number;
    private String ExpireDate;
    private int CVV;
    private String bank;
    CreditCard(){

    }
    CreditCard(String number, String Date, int CVV, String bank){
        this.number=number;
        this.ExpireDate=Date;
        this.CVV=CVV;
        this.bank=bank;
    }
    public String getDate(){
        return this.number;
    }
    public String getBank(){
        return this.bank;
    }
    public String getExpireDate(){
        return this.ExpireDate;
    }
    public int getCVV(){
        return this.CVV;
    }
public void setNumber(String number){
    this.number=number;
}
    public void setDate(String Date){
        this.ExpireDate=Date;
    }
    public void setBank(String bank){
        this.bank=bank;
    }
    public void setCVV(int CVV){
        this.CVV=CVV;
    }
}
