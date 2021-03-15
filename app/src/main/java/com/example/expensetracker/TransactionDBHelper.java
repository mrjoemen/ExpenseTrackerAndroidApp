package com.example.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TransactionDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mytransactions.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation sql statement
    private static final String CREATE_TABLE_TRANSACTION =
            "create table transactions (_id integer primary key autoincrement, " +
                    "transactionAmount float, transactionDate text, description text);";

    public TransactionDBHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TransactionDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion +
                "which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }
}
