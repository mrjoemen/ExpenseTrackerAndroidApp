package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Transaction currentTransaction;
    /* TODO add currentTransaction.setDate(selectedTime) in datePicker dialog.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTransaction = new Transaction();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initTextChangedEvents(){

        //collect transaction description info from the UI
        final EditText etTransactionDescr = findViewById(R.id.editDescription/* change for the actual id*/);
        etTransactionDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentTransaction.setDescription(etTransactionDescr.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //collect amount info from UI
        final EditText etTransactionAmount = findViewById(R.id.editAmount/* change for the actual id*/);
        etTransactionAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                currentTransaction.setAmount(Integer.parseInt(etTransactionAmount.getText().toString()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}