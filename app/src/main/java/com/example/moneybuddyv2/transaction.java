package com.example.moneybuddyv2;

import java.util.Date;

//categories
//1 Not Mentioned
//2 Academic
//3 Entertainment
//4 Rent
//5 Shopping

public class transaction {
    private static int count = 1;

    private int sno;
    private double amount;
    private String date;
    private String note;

    private int category;

    transaction(  double amount, String date,int category, String s) {
        sno = count++;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = s;
    }

    //constructor for smsReciever
    transaction(double amount, String date) {
        sno= count++;
        this.amount = amount;
        this.date = date;
        this.category = 1;
        this.note = "Bank Transaction";
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
