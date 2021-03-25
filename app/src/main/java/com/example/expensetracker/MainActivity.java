package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<Transaction> transactions;



    /* TODO:
        add currentTransaction.setDate(selectedTime) in datePicker dialog.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInButton();
        initOutButton();
        initMenuItems();
        initCurrentBalance();
        //initChangeTransactionDateButton();

        //pulling info  from DB for recycler view
//        TransactionDataSource ds = new TransactionDataSource(this);
//        try {
//            ds.open();
//            transactions = ds.getTransactions();
//            ds.close();
//            RecyclerView contactList = findViewById(R.id.rvTransactions);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            contactList.setLayoutManager(layoutManager);
//            contactAdapter = new ContactAdapter(contacts, ContactListActivity.this);
//            contactAdapter.setOnItemClickListener(onItemClickListener);
//            contactList.setAdapter(contactAdapter);
//        }
//        catch (Exception e) {
//            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
//        }
    }


//    @Override
//    public void didFinishDatePickerDialog(Calendar selectedTime) {
//        TextView transDate = findViewById(R.id.textTransactionDate);
//        transDate.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
//        Log.i("Hello there", "hello");
//
//        //need current Transaction
//        currentTransaction.setDate(selectedTime);
//    }
//
//
//    //click listener and get datapicker dialog
//    private void initChangeTransactionDateButton(){
//        Button changeDate = findViewById(R.id.btnChangeDate);
//        changeDate.setOnClickListener(view -> {
//            FragmentManager fm = getSupportFragmentManager();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(); // We create the dialog here
//            datePickerDialog.show(fm, "DatePick"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
//        });
//    }
//
//    //init toggle button and set for editing when checked
//    private void initToggleButton() {
//        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
//        editToggle.setOnClickListener(view -> setForEditing(editToggle.isChecked()));
//    }

    //needed still
    private void setForEditing(boolean checked) {
    }

    private void initInButton(){
        Button inButton = findViewById(R.id.inButton);
        inButton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, DebitActivity.class);
            startActivity(intent);
        });
    }
    private void initOutButton(){
        Button inButton = findViewById(R.id.outButton);
        inButton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, CreditActivity.class);
            startActivity(intent);
        });
    }

    private void initCurrentBalance(){
        //ds instance for accessing instance method later
        TransactionDataSource dataSource = new TransactionDataSource(MainActivity.this);
        TextView balance = findViewById(R.id.dollarText);
        balance.setText("$" + dataSource.getBalance());
    }

    private void initMenuItems(){

        //investment menu icon links to Investment layout
        ImageButton investButton = findViewById(R.id.investmentButton);
        investButton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, InvestmentOptions.class);
            startActivity(intent);
        });
    }


}