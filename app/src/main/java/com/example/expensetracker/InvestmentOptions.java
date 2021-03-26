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
    final String SPY_url = NYSurlBuilder("SPY");
    final String BTC_url = criptoURLBuilder("bitcoin");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investments);
        initMenuItems();
        SPY_TextView = findViewById(R.id.TikPrice);
        SPY_TextView.setText("Loading...");

        //calling for inner class passing URL for the stock
        new MyTask().execute(SPY_url);
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
        }
    }

    protected String NYSurlBuilder(String stockSym){
        return "https://www.marketwatch.com/investing/fund/" + stockSym + "/download-data?siteid=mktw&date=&x=0&y=0";
    }
    protected String criptoURLBuilder(String currencyName){

        return "https://www.coindesk.com/price/" + currencyName;
    }
}
