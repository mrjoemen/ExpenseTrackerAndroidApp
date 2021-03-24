package com.example.expensetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity/*implements DatePickerDialog.SaveDateListener*/{

    private Transaction currentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
       // initTextChangedEvents();
        currentTransaction = new Transaction();
    }

/*
    //method for reading inputs from the screen
    private void initTextChangedEvents(){

        //collect transaction description info from the UI
        final EditText etTransactionDescr = findViewById(R.id.editDescription); // change for the actual id
        etTransactionDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setDescription(etTransactionDescr.getText().toString());
            }
        });

        //collect amount info from UI
        final EditText etTransactionAmount = findViewById(R.id.editAmount); // change for the actual id
        etTransactionAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setAmount(Integer.parseInt(etTransactionAmount.getText().toString()));
            }
        });

        //Transaction Type info collection
        final EditText etTransactionType = findViewById(R.id.editType); // change for the actual id
        etTransactionType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentTransaction.setType(etTransactionType.getText().toString());
            }
        });

    }
    */
/*
    private void initSaveButton(){
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener((View view) ->{
            boolean wasSuccessful = false;
            TransactionDataSource dataSource = new TransactionDataSource(TransactionActivity.this); //data source receives context as a parameter
            try {
                dataSource.open(); //open the database

                if (currentTransaction.getTransactionID() == -1) { // remember that a new contact has an id of -1
                    wasSuccessful = dataSource.insertTransaction(currentTransaction); // if the contact does not exist, then create a new one
                }
                if (wasSuccessful) {
                    int newID = dataSource.getLastContactID();
                    currentTransaction.setTransactionID(newID);
                }
                else {
                    wasSuccessful = dataSource.updateTransaction(currentTransaction); // if it does exist, then just update it
                }
                dataSource.close();
            }
            catch (Exception e) {
                wasSuccessful = false;
            }
            if (wasSuccessful) {
                ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                editToggle.toggle();
                setForEditing(false);
            }
        });

 */


    //click listener and get datapicker dialog
//    private void initChangeTransactionDateButton(){
//        Button changeDate = findViewById(R.id.btnChangeDate);
//        changeDate.setOnClickListener(view -> {
//            FragmentManager fm = getSupportFragmentManager();
//            DatePickerDialog datePickerDialog = new DatePickerDialog(); // We create the dialog here
//            datePickerDialog.show(fm, "DatePick"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
//        });
//    }
//
//
//    @Override
//    public void didFinishDatePickerDialog(Calendar selectedTime) {
//        TextView transDate = findViewById(R.id.textTransactionDate);
//        transDate.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
//        Log.i("Hello there", "hello");
//
//        //need current Transaction
//        currentTransaction.setDate(selectedTime);
//    }
}
