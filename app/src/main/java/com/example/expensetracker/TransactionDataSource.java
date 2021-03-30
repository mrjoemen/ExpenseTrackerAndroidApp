package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class TransactionDataSource {
    private SQLiteDatabase database;
    private TransactionDBHelper dbHelper;

    public TransactionDataSource(Context context){
        dbHelper = new TransactionDBHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean insertTransaction(Transaction t){
        boolean didSucceed = false;
        try{
            ContentValues initialValues = new ContentValues();

            initialValues.put("description", t.getDescription());
            initialValues.put("transactionType", t.getType());
            initialValues.put("transactionAmount", t.getAmount());
            initialValues.put("transactionDate", String.valueOf(t.getDate().getTimeInMillis()));

            didSucceed = database.insert("transactions", null, initialValues) > 0;
        }
        catch (Exception e){

        }

        return didSucceed;
    }

    public boolean updateTransaction(Transaction t){
        boolean didSucceed = false;

        try{
            Long rowId = (long)t.getTransactionID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("description", t.getDescription());
            updateValues.put("transactionType", t.getType());
            updateValues.put("transactionAmount", t.getAmount());
            updateValues.put("transactionDate", String.valueOf(t.getDate().getTimeInMillis()));

            didSucceed = database.update("transactions", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e){

        }

        return didSucceed;
    }

    public int getLastTransactionId() {
        int lastId;

        try {
            String query = "Select MAX(_id) from transactions";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<Transaction> getTransactions(String sortField, String sortOrder){

        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction newTransaction;

        try {
            String query = "SELECT * FROM transactions ORDER BY " + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newTransaction = new Transaction();
                newTransaction.setTransactionID(cursor.getInt(0));
                newTransaction.setAmount(cursor.getFloat(1));
                newTransaction.setType(cursor.getString(2));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(3)));
                newTransaction.setDate(calendar);
                newTransaction.setDescription(cursor.getString(4));
                transactions.add(newTransaction);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            transactions = new ArrayList<Transaction>();
        }
        return transactions;
    }

    public float getBalance(){
        database = dbHelper.getWritableDatabase();
        float balance = 0;
        try {
            String query = "SELECT transactionAmount FROM transactions"; /*ORDER BY"/* + sortField + " " + sortOrder + ";";*/
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                balance += (cursor.getFloat(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            Log.w(TransactionDataSource.class.getName(), "error accessing balance");
        }
        return balance;

    }

    public boolean deleteTransaction(int transactionId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("transactions","_id=" + transactionId, null) > 0;
        }
        catch (Exception e){
            //pass
        }
        return didDelete;
    }

    public Transaction getSpecifiedTransaction(int transactionId) {
        Transaction transaction = new Transaction();
        String query = "SELECT * FROM transactions WHERE _id =" + transactionId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            transaction.setTransactionID(cursor.getInt(0));
            transaction.setAmount(cursor.getFloat(1));
            transaction.setType(cursor.getString(2));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(3)));
            transaction.setDate(calendar);
            transaction.setDescription(cursor.getString(4));

            cursor.close();
        }
        return transaction;
    }

}
