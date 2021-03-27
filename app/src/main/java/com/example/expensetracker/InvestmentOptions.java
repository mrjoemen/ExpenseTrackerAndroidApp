package com.example.expensetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InvestmentOptions extends AppCompatActivity {
    TextView SPY_TextView;
    TextView BTC_TextView;
    TextView Stocks_Afforded;
    final String SPY_url = NYSurlBuilder("SPY");
    //final String BTC_url = criptoURLBuilder("BTCUSD");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investments);
        initMenuItems();
        initCurrentBalance();
        SPY_TextView = findViewById(R.id.TikPrice);
        SPY_TextView.setText("Loading...");
        BTC_TextView = findViewById(R.id.btcPrice);
        BTC_TextView.setText("Loading...");

        Stocks_Afforded = findViewById(R.id.textAfforded);

        //calling for inner class passing URL for the stock
        new MyTask().execute(SPY_url);
       // new MyTask().execute(BTC_url);

    }

    private void initMenuItems(){

        //investment menu icon links to Investment layout
        ImageButton investButton = findViewById(R.id.homeButton);
        investButton.setOnClickListener(view ->{
            Intent intent = new Intent(InvestmentOptions.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initCurrentBalance(){
        //ds instance for accessing instance method later
        TransactionDataSource dataSource = new TransactionDataSource(InvestmentOptions.this);
        TextView balance = findViewById(R.id.dollarText);
        balance.setText("$" + dataSource.getBalance());
    }

    //class to call url Asyncronously where we pass the url and the TextView item to be set with the returned price
    private class MyTask extends AsyncTask<Object, Void, Void> {
       String quote = "not found";
       TextView tv;

        //call url
        @Override
        protected Void doInBackground(Object... link) {

            try {
                URL url = new URL((String)link[0]);// assigning passed url for HTTP call
                URLConnection urlConnection = url.openConnection();
                InputStreamReader inStream = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader buff = new BufferedReader(inStream);

                String line = buff.readLine();

                //looping over results
                while (line != null) {
                    //once found the segment, pull the price
                    if (line.contains("price\":\"")) {
                        int target = line.indexOf("price\":\"");
                        int decimal = line.indexOf(".", target);
                        int start = decimal;
                        while (line.charAt(start) != '\"') {
                            start--;
                        }

                        //assign the result to global var quote
                        quote = line.substring(start + 1, decimal + 3);
                        break;
                    }
                    line = buff.readLine();
                }
            }
                        catch (Exception e){

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void price) {
            //set the above global TextView to the pulled quote, found in doInBackground
            SPY_TextView.setText("SPY today is $"+ quote);
            double stockPrice = Double.parseDouble(quote);
            TransactionDataSource dataSource = new TransactionDataSource(InvestmentOptions.this);
            Double balance = Double.valueOf(dataSource.getBalance());
            double afforded = balance / stockPrice;
            Stocks_Afforded.setText("Number of shares you could purchase:" + afforded);
        }

        //protected void onPostExecuteBtc(Void price){
      //  BTC_TextView.setText("BTC today is $" + quote);
       // }

    }

    protected String NYSurlBuilder(String stockSym) {
        return "https://www.marketwatch.com/investing/fund/" + stockSym + "/download-data?siteid=mktw&date=&x=0&y=0";
    }

    /*protected String criptoURLBuilder(String currencyName){

        return "https://www.marketwatch.com/investing/fund/" + currencyName + "/download-data?siteid=mktw&date=&x=0&y=0";
    }*/
}
