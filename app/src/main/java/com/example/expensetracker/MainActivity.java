package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<Transaction> transactions;
    TransactionAdapter transactionAdapter;
    /*TODO:
     *  - DONE: List and editing item
     *  - DONE: DB work
     *  - DONE: There is a bug that will crash the app if I press anything but a number for inflow or outflow
     *  - In-progress: Sorting
     *  - In-progress: Investing API implementation
     *  - In-progress: Delete off of list
     *  - I would like to have each list item be green if it's inflow and red if it's an outflow, for now
     *    I will add this as an additional feature that we may implement, but for now I will skip it
     *  - Cancel button for debit and credit needs to go back to home page
     *  - We need a way to have limited options for type for sorting purposes
     *  - We should also consider a cleaner way to present date by making the button look nicer and bring date to the left*/

    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        int transactionId = transactions.get(position).getTransactionID();

        Intent intent;
        if (transactions.get(position).getAmount() < 0) {
            intent = new Intent(MainActivity.this, CreditActivity.class);
        }
        else {
            intent = new Intent(MainActivity.this, DebitActivity.class);
        }

        intent.putExtra("transactionId", transactionId);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TransactionDataSource ds = new TransactionDataSource(this);
        initInButton();
        initOutButton();
        initMenuItems();
        initCurrentBalance();
        initDeleteSwitch();
        //initChangeTransactionDateButton();

        try {
            ds.open();
            transactions = ds.getTransactions();
            ds.close();
            RecyclerView transactionList = findViewById(R.id.rvTransactions);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            transactionList.setLayoutManager(layoutManager);
            transactionAdapter = new TransactionAdapter(transactions, this);
            transactionAdapter.setmOnItemClickListener(onItemClickListener);
            transactionList.setAdapter(transactionAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving transactions", Toast.LENGTH_LONG).show();
        }
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

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.deleteSwtich);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean status = buttonView.isChecked();
                transactionAdapter.setDelete(status);
                transactionAdapter.notifyDataSetChanged();
            }
        });
    }


}