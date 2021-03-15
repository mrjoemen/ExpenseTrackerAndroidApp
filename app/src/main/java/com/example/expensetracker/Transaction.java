package com.example.expensetracker;

import java.util.Calendar;

public class Transaction {
    private float amount;
    private Calendar date;
    private String description;
    private int transactionID;

    public Transaction(){
        transactionID = -1;
        date = Calendar.getInstance();
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
}
