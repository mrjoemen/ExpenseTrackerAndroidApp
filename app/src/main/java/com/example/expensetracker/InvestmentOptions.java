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
import java.util.ArrayList;

public class InvestmentOptions extends AppCompatActivity {
    TextView AAPL_TextView;
    TextView GoogleTextView;
    TextView totalStockApple;
    TextView totalGoogleStocks;
    TextView TextV1;

    final String APPL_url = NYSurlBuilder("aapl");
    final String GOOGLE_url = NYSurlBuilder("goog");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investments);
        initMenuItems();
        initCurrentBalance();
        AAPL_TextView = findViewById(R.id.AAPLPrice);
        AAPL_TextView.setText("Loading...");
        GoogleTextView = findViewById(R.id.googleTextView);
        GoogleTextView.setText("Loading...");


        totalStockApple = findViewById(R.id.totalStockAapl);
        totalGoogleStocks = findViewById(R.id.totalGoogleStocks);

        //calling for inner class passing URL for the stock
        new MyTask().execute(APPL_url, GOOGLE_url);


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
    private class MyTask extends AsyncTask<String, Void, Void> {

       //creating an array to hold found quotes in doInBackground method
        //then using it to assign values to TextViews
        ArrayList<String> quotes = new ArrayList<>();

        //call url
        @Override
        protected Void doInBackground(String... urls) {

            for (String s : urls) {

                try {

                    URL url = new URL(s);// assigning passed url for HTTP call
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

                            //assign the result to ArrayList quote
                            //end loop once the price found and go to next one in forEach loop
                            quotes.add(line.substring(start + 1, decimal + 3));
                            break;
                        }
                        line = buff.readLine();

                    }
                } catch (Exception ignored) {

                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void price) {
            //set the above global TextViews to the pulled quotes, found in doInBackground
            AAPL_TextView.setText("$"+quotes.get(0));
            GoogleTextView.setText("$" + quotes.get(1));


            TransactionDataSource dataSource = new TransactionDataSource(InvestmentOptions.this);
            Double balance = Double.valueOf(dataSource.getBalance());

            double totalStockAppl = balance / Double.parseDouble(quotes.get(0).replace(",", ""));
            double totalStockGoogle = balance / Double.parseDouble(quotes.get(1).replace(",", ""));


            totalStockApple.setText(String.format("Number of shares you could purchase: %.03f" , totalStockAppl));
            totalGoogleStocks.setText(String.format("Number of shares you could purchase: %.03f" , totalStockGoogle));



        }
    }

    //method to build a custom URL based on the input symbol
    protected String NYSurlBuilder(String stockSym){
        return "https://www.marketwatch.com/investing/fund/" + stockSym + "/download-data?siteid=mktw&date=&x=0&y=0";
    }

}
