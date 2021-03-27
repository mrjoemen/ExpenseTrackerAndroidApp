package com.example.expensetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InvestmentOptions extends AppCompatActivity {
    TextView SPY_TextView;
    TextView GoogleTextView;
    final String SPY_url = NYSurlBuilder("spy");
    final String GOOGLE_url = NYSurlBuilder("goog");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investments);
        initMenuItems();
        SPY_TextView = findViewById(R.id.SPYPrice);
        SPY_TextView.setText("Loading...");
        GoogleTextView = findViewById(R.id.googleTextView);
        GoogleTextView.setText("Loading...");

        //calling for inner class passing URLs for the stock
        new MyTask().execute(SPY_url, GOOGLE_url);


    }

    private void initMenuItems(){

        //investment menu icon links to Investment layout
        ImageButton investButton = findViewById(R.id.homeButton);
        investButton.setOnClickListener(view ->{
            Intent intent = new Intent(InvestmentOptions.this, MainActivity.class);
            startActivity(intent);
        });
    }

    //class to call url Asyncronously where we pass the url and the TextView item to be set with the returned price
    private class MyTask extends AsyncTask<String, Void, Void> {

       //creating an array to hold found quotes in doInBackground method
        //then using it to assign values to TextViews
        ArrayList<String> quotes = new ArrayList<>();


        @Override
        protected Void doInBackground(String... urls) {

            for (String s : urls) {//just looping over passed strings

                try {
                    // assigning passed url for HTTP call
                    URL url = new URL(s);
                    //initiating connection
                    URLConnection urlConnection = url.openConnection();
                    //collect HTML to inputStream
                    InputStreamReader inStream = new InputStreamReader(urlConnection.getInputStream());
                    //convert to a readable BufferedReader
                    BufferedReader buff = new BufferedReader(inStream);

                    //read the first line
                    String line = buff.readLine();

                    //looping over results until found desired value or end
                    while (line != null) {

                        //once found the specific element
                        // the price will follow it, pull the price and go to next URL

                        if (line.contains("price\":\"")) {//<-- this is the specific element
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
            //if more assignment required, pass extra URLs and assign text here
            SPY_TextView.setText("SPY price today is $" + quotes.get(0));
            GoogleTextView.setText("GOOG price today is $" + quotes.get(1));

        }
    }

    //method to build a custom URL based on the input symbol
    protected String NYSurlBuilder(String stockSym){
        return "https://www.marketwatch.com/investing/fund/" + stockSym + "/download-data?siteid=mktw&date=&x=0&y=0";
    }

}
