package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public int getLastContactID() {
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
}
