package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class CreditActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener{

    private Transaction currentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        initTextChangedEvents();
        initCreditSaveButton();
        currentTransaction = new Transaction();
    }


    //method for reading inputs from the screen
    private void initTextChangedEvents(){

        //collect transaction description info from the UI
        final EditText etCreditDescr = findViewById(R.id.editCreditDescription); // change for the actual id
        etCreditDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setDescription(etCreditDescr.getText().toString());
            }
        });

        //collect amount info from UI
        final EditText etCreditAmount = findViewById(R.id.editCreditAmount); // change for the actual id
        etCreditAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setAmount(-1 * Integer.parseInt(etCreditAmount.getText().toString()));
            }
        });

        //Transaction Type info collection
        final EditText etCreditType = findViewById(R.id.editCreditType); // change for the actual id
        etCreditType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setType(etCreditType.getText().toString());
            }
        });

    }


    //activating save button and process the push of data to DB
    private void initCreditSaveButton() {
        Button saveButton = findViewById(R.id.creditSaveButton);
        saveButton.setOnClickListener((View view) -> {
            boolean wasSuccessful = false;
            TransactionDataSource dataSource = new TransactionDataSource(CreditActivity.this); //data source receives context as a parameter
            try {
                dataSource.open(); //open the database

                if (currentTransaction.getTransactionID() == -1) { // remember that a new transaction has an id of -1
                    wasSuccessful = dataSource.insertTransaction(currentTransaction); // if the contact does not exist, then create a new one
                }
                if (wasSuccessful) {
                    int newID = dataSource.getLastContactID();
                    currentTransaction.setTransactionID(newID);
                } else {
                    wasSuccessful = dataSource.updateTransaction(currentTransaction); // if it does exist, then just update it
                }
                dataSource.close();
            } catch (Exception e) {
                wasSuccessful = false;
            }

            //just a helper to confirm a successful entry in DB
            if(wasSuccessful){
                Log.w(DebitActivity.class.getName(),"Credit  Entry registered successfully");
            }

            //switch back to main
            Intent intent = new Intent(CreditActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }


    //click listener and get datapicker dialog
    private void initChangeCreditDateButton(){
        Button changeDate = findViewById(R.id.debitDateButton);
        changeDate.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog(); // We create the dialog here
            datePickerDialog.show(fm, "DatePick"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
        });
    }


    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView transDate = findViewById(R.id.editDebitDate);
        transDate.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        Log.i("Hello there", "hello");

        //need current Transaction
        currentTransaction.setDate(selectedTime);
    }
}
