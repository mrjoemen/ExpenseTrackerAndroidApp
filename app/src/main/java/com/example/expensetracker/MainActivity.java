package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {


    /* TODO:
        add currentTransaction.setDate(selectedTime) in datePicker dialog.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initChangeTransactionDateButton();
    }


    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView transDate = findViewById(R.id.textTransactionDate);
        transDate.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        Log.i("Hello there", "hello");

        //need current Transaction
        currentTransaction.setDate(selectedTime);
    }


    //click listener and get datapicker dialog
    private void initChangeTransactionDateButton(){
        Button changeDate = findViewById(R.id.btnChangeDate);
        changeDate.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog(); // We create the dialog here
            datePickerDialog.show(fm, "DatePick"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
        });
    }

    //init toggle button and set for editing when checked
    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(view -> setForEditing(editToggle.isChecked()));
    }

    //needed still
    private void setForEditing(boolean checked) {
    }

}