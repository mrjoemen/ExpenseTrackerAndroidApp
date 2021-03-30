package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSettings();
        //initSettingsButton();
        initSortByClick();
        initSortOrderClick();
        initHomeButton();
        initMenuItems();
    }

    //init home button
   private void initHomeButton() {
        ImageButton homeButton = findViewById(R.id.homeButton); // Variable to hold the ImageButton
       // Listener is added to the ImageButton to make it respond to different things
       homeButton.setOnClickListener(view -> {
           Intent intent = new Intent(TransactionSettingsActivity.this, MainActivity.class); // A mew Intent is created, the Intent's constructors requires reference to the current activity and know what activity to start
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // An Intent flag is set to alert the operating system to not make multiple copes of the same activity
           startActivity(intent); // And listen
       });
    }


    /*private void initSettingsButton() {
        ImageButton ibSetting = findViewById(R.id.settingsButton);
        ibSetting.setEnabled(false);
    }
    */


    private void initMenuItems(){

        //investment menu icon links to Investment layout
        ImageButton investButton = findViewById(R.id.investmentButton);
        investButton.setOnClickListener(view ->{
            Intent intent = new Intent(TransactionSettingsActivity.this, InvestmentOptions.class);
            startActivity(intent);
        });
    }


    private void initSettings() {
        String sortBy = getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).getString("sortfield","transactionname");
        String sortOrder = getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbAmount = findViewById(R.id.radioAmount);
        RadioButton rbType = findViewById(R.id.radioType);
        RadioButton rbDate = findViewById(R.id.radioDate);

        if(sortBy.equalsIgnoreCase("transactionAmount")) {
            rbAmount.setChecked(true);
        }

        else if (sortBy.equalsIgnoreCase("transactionType")) {
            rbType.setChecked(true);
        }

        else {
            rbDate.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);

        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rbAmount = findViewById(R.id.radioAmount);
            RadioButton rbType = findViewById(R.id.radioType);

            if (rbAmount.isChecked()) {
                getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortfield", "transactionAmount").apply();
            }

            else if (rbType.isChecked()) {
                getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortfield", "transactionType").apply();
            }

            else {
                getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortfield", "transactionDate").apply();
            }
        });
    }


    private void initSortOrderClick() {
        RadioGroup rgSortOrder =findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(((group, checkedId) -> {
            RadioButton rbAscending = findViewById(R.id.radioAscending);

            if(rbAscending.isChecked()) {
                getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortorder", "ASC").apply();
            }
            else {
                getSharedPreferences("ExpenseTrackerPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortorder", "DESC").apply();
            }
        }));
    }
}
